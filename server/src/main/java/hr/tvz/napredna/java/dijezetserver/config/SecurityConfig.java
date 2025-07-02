package hr.tvz.napredna.java.dijezetserver.config;

import hr.tvz.napredna.java.dijezetserver.security.JwtTokenCookieFilter;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final String jwtSecret;
    private final CorsConfig corsConfig;

    public SecurityConfig(UserDetailsService userDetailsService, @Value("${jwt.secret}") String jwtSecret, CorsConfig corsConfig) {
        this.userDetailsService = userDetailsService;
        this.jwtSecret = jwtSecret;
        this.corsConfig = corsConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenCookieFilter jwtTokenFromCookieFilter(SecretKey jwtSecretKey) {
        return new JwtTokenCookieFilter(userDetailsService, jwtSecretKey);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenCookieFilter jwtTokenFromCookieFilter) throws Exception {
        http.authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(ApiPaths.LOGIN, ApiPaths.REGISTER, ApiPaths.REFRESH_TOKEN).permitAll()
                                .requestMatchers(ApiPaths.SWAGGER).permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()));

        http.addFilterBefore(jwtTokenFromCookieFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }
}

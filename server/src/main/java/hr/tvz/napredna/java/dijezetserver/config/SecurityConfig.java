package hr.tvz.napredna.java.dijezetserver.config;

import hr.tvz.napredna.java.dijezetserver.security.JwtTokenCookieFilter;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final String jwtSecret;

    public SecurityConfig(UserDetailsService userDetailsService, @Value("${jwt.secret}") String jwtSecret) {
        this.userDetailsService = userDetailsService;
        this.jwtSecret = jwtSecret;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenCookieFilter jwtTokenFromCookieFilter() {
        return new JwtTokenCookieFilter(userDetailsService, Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)));
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenCookieFilter jwtTokenFromCookieFilter) throws Exception {
        http.authorizeHttpRequests((authorize) ->
                authorize
                        .requestMatchers(ApiPaths.LOGIN, ApiPaths.REGISTER).permitAll()
                        .requestMatchers(ApiPaths.SWAGGER).permitAll()
                        .anyRequest().authenticated()
        ).csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtTokenFromCookieFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

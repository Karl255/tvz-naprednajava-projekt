package hr.tvz.napredna.java.dijezetserver.security;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class JwtTokenCookieFilterTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecretKey jwtSecretKey;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void shouldAuthenticateRequestWithValidToken() throws Exception {
        when(userDetailsService.loadUserByUsername(USER.getUsername())).thenReturn(USER);

        String jwt = JwtUtils.createToken(USER_DTO, jwtSecretKey);
        Cookie cookie = new Cookie("token", jwt);

        mockMvc.perform(get(ApiPaths.USER_PROFILE).cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSkipFilterWhenNoCookiesPresent() throws Exception {
        mockMvc.perform(get(ApiPaths.USER_PROFILE))
                .andExpect(status().isForbidden()); // ili isOk(), ovisno o tvojoj security konfiguraciji
    }

    @Test
    void shouldSkipFilterWhenCookieIsNotToken() throws Exception {
        Cookie irrelevantCookie = new Cookie("session", "some-value");

        mockMvc.perform(get(ApiPaths.USER_PROFILE).cookie(irrelevantCookie))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldSkipAuthenticationWhenTokenIsInvalid() throws Exception {
        Cookie tokenCookie = new Cookie("token", "invalid");

        mockMvc.perform(get(ApiPaths.USER_PROFILE).cookie(tokenCookie))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnUnauthorizedWhenTokenIsExpired() throws Exception {
        String expiredToken = JwtUtils.createToken(USER_DTO, jwtSecretKey);

        try (MockedStatic<JwtUtils> jwtUtilsMockedStatic = mockStatic(JwtUtils.class)) {
            jwtUtilsMockedStatic.when(() -> JwtUtils.validateToken(expiredToken, jwtSecretKey))
                    .thenReturn(true);

            jwtUtilsMockedStatic.when(() -> JwtUtils.extractUsername(expiredToken, jwtSecretKey))
                    .thenThrow(new ExpiredJwtException(null, null, "JWT expired"));

            Cookie cookie = new Cookie("token", expiredToken);

            mockMvc.perform(get(ApiPaths.USER_PROFILE).cookie(cookie))
                    .andExpect(status().isUnauthorized());
        }
    }

}
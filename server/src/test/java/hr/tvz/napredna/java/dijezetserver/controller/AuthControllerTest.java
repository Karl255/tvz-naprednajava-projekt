package hr.tvz.napredna.java.dijezetserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.LoginDto;
import hr.tvz.napredna.java.dijezetserver.request.RefreshTokenRequest;
import hr.tvz.napredna.java.dijezetserver.service.UserService;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseTest {

    private final Authentication authentication = mock(Authentication.class);
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private AuthenticationManager authenticationManager;

    private SecretKey secretKey;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        byte[] keyBytes = "super-secret-key-1234567890123456".getBytes(StandardCharsets.UTF_8);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void shouldLoginUser() throws Exception {
        when(userService.getByUserName(any())).thenReturn(USER_DTO);
        when(userService.getRefreshToken(any())).thenReturn(USER_REFRESH_TOKEN_ACTIVE.getRefreshToken());

        mockMvc.perform(post(ApiPaths.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(USER_REQUEST)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRegisterUser() throws Exception {
        when(userService.create(any())).thenReturn(USER_DTO);
        mockMvc.perform(post(ApiPaths.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(USER_REQUEST)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldLoginUserAndGetProfile() throws Exception {
        when(userService.getByUserName(any())).thenReturn(USER_DTO);
        when(userService.getRefreshToken(any())).thenReturn(USER_REFRESH_TOKEN_ACTIVE.getRefreshToken());
        when(userService.getByRefreshToken(any())).thenReturn(USER_DTO);

        MvcResult result = mockMvc.perform(post(ApiPaths.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(USER_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("token"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        LoginDto loginDto = objectMapper.readValue(responseBody, LoginDto.class);

        assertNotNull(loginDto);

        mockMvc.perform(post(ApiPaths.REFRESH_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new RefreshTokenRequest(loginDto.refreshToken()))))
                .andExpect(status().isOk());
    }
}

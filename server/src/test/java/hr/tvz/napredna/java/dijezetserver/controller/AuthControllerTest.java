package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.request.RefreshTokenRequest;
import hr.tvz.napredna.java.dijezetserver.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;
    @MockitoBean
    private AuthenticationManager authenticationManager;
    @MockitoBean
    private SecretKey secretKey;

    private final Authentication authentication = mock(Authentication.class);

//    @Test
//    void shouldLoginUser() throws Exception {
//        when(userService.getByUserName(any())).thenReturn(USER_DTO);
//        when(userService.getRefreshToken(any())).thenReturn(USER_REFRESH_TOKEN_ACTIVE.getRefreshToken());
//
//        mockMvc.perform(post(ApiPaths.LOGIN)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(toJson(USER_REQUEST)))
//                .andExpect(status().isOk());
//    }

    @Test
    void shouldReturnUnauthorizedOnLogin() throws Exception {
        doThrow(new BadCredentialsException("Bad credentials, try again!")).when(authenticationManager).authenticate(any());
        mockMvc.perform(post(ApiPaths.LOGIN))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRegisterUser() throws Exception {
        when(userService.create(any())).thenReturn(USER_DTO);
        mockMvc.perform(post(ApiPaths.REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(USER_REQUEST)))
                .andExpect(status().isOk());
    }
}

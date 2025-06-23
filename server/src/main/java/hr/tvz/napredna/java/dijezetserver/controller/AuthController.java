package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.LoginDto;
import hr.tvz.napredna.java.dijezetserver.dto.UserDto;
import hr.tvz.napredna.java.dijezetserver.exceptions.ApiException;
import hr.tvz.napredna.java.dijezetserver.request.RefreshTokenRequest;
import hr.tvz.napredna.java.dijezetserver.request.UserRequest;
import hr.tvz.napredna.java.dijezetserver.security.JwtUtils;
import hr.tvz.napredna.java.dijezetserver.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.time.Duration;

@RestController
@Tag(name = "Authentication", description = "User login and registration")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecretKey jwtSecretKey;

    @PostMapping(ApiPaths.LOGIN)
    public ResponseEntity<LoginDto> login(@RequestBody UserRequest userRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw ApiException.unauthorized("Bad Credentials, try again!");
        }

        UserDto user = userService.getByUserName(userRequest.getUsername());
        String refreshToken = userService.getRefreshToken(userRequest.getUsername());
        LoginDto loginDto = new LoginDto(user, refreshToken);

        ResponseCookie cookie = ResponseCookie.from("token", JwtUtils.createToken(user, jwtSecretKey))
                .path(ApiPaths.BASE_API_URL)
                .maxAge(Duration.ofHours(1))
                .httpOnly(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginDto);
    }

    @PostMapping(ApiPaths.REFRESH_TOKEN)
    public ResponseEntity<LoginDto> refreshToken(@RequestBody RefreshTokenRequest tokenRequest) {
        UserDto user = userService.getByRefreshToken(tokenRequest.getToken());
        String refreshToken = userService.getRefreshToken(user.getUsername());
        LoginDto loginDto = new LoginDto(user, refreshToken);

        ResponseCookie cookie = ResponseCookie.from("token", JwtUtils.createToken(user, jwtSecretKey))
                .path(ApiPaths.BASE_API_URL)
                .maxAge(Duration.ofHours(1))
                .httpOnly(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginDto);
    }

    @PostMapping(ApiPaths.REGISTER)
    public void register(@RequestBody @Valid UserRequest userRequest) {
        userService.create(userRequest);
    }
}
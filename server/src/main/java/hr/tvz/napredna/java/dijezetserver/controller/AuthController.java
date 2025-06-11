package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.request.UserRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "User login and registration")
@AllArgsConstructor
public class AuthController {

    @PostMapping(ApiPaths.LOGIN)
    public void login(@RequestBody UserRequest userRequest) {
    }

    @PostMapping(ApiPaths.REGISTER)
    public void register(@RequestBody UserRequest userRequest) {
    }
}

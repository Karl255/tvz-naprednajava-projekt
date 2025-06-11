package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.UserDto;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ApiPaths.USER)
@Tag(name = "User", description = "CRUD operations for application users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/profile")
    public UserDto getUserProfile(@AuthenticationPrincipal User user) {
        return userService.getProfile(user);
    }
}

package hr.tvz.napredna.java.dijezetserver.service.impl;

import hr.tvz.napredna.java.dijezetserver.dto.UserDto;
import hr.tvz.napredna.java.dijezetserver.exceptions.ApiException;
import hr.tvz.napredna.java.dijezetserver.mapper.UserMapper;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.model.UserRefreshToken;
import hr.tvz.napredna.java.dijezetserver.model.UserRole;
import hr.tvz.napredna.java.dijezetserver.repository.UserRefreshTokenRepository;
import hr.tvz.napredna.java.dijezetserver.repository.UserRepository;
import hr.tvz.napredna.java.dijezetserver.request.UserRequest;
import hr.tvz.napredna.java.dijezetserver.service.UserRefreshTokenService;
import hr.tvz.napredna.java.dijezetserver.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserRefreshTokenService {

    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public UserDto getByUserName(String username) {
        return toDto(userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User " + username + " not found")));
    }

    @Override
    public UserDto getProfile(User user) {
        return toDto(user);
    }

    @Override
    public UserDto create(UserRequest userRequest) {
        Optional<User> existingUser = userRepository.findByUsername(userRequest.getUsername());

        if (existingUser.isPresent()) {
            throw ApiException.badRequest("Username " + userRequest.getUsername() + " already exists");
        }

        User newUser = UserMapper.toEntity(userRequest.getUsername(), passwordEncoder.encode(userRequest.getPassword()), UserRole.USER);

        return toDto(userRepository.save(newUser));
    }

    @Override
    public UserDto update(UserRequest userRequest) {
        Optional<User> existingUser = userRepository.findByUsername(userRequest.getUsername());

        if (existingUser.isEmpty()) {
            throw ApiException.notFound("Username " + userRequest.getUsername() + " does not exist");
        }

        User newUser = existingUser.get();
        newUser.setHashedPassword(passwordEncoder.encode(userRequest.getPassword()));

        return toDto(userRepository.save(newUser));
    }

    @Override
    public void delete(Long id) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            throw ApiException.notFound("User with ID " + id + " does not exist");
        }

        userRepository.delete(existingUser.get());
    }

    @Override
    public String getRefreshToken(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);

        if (existingUser.isEmpty()) {
            throw ApiException.notFound("User with username " + username + " does not exist");
        }

        User user = existingUser.get();
        Optional<UserRefreshToken> existingRefreshToken = userRefreshTokenRepository.findByUser(user);

        if (existingRefreshToken.isEmpty()) {
            return createNewRefreshToken(user);
        }

        UserRefreshToken refreshToken = existingRefreshToken.get();

        if (refreshToken.getExpiresAt().isAfter(LocalDateTime.now())) {
            return existingRefreshToken.get().getRefreshToken();
        } else {
            userRefreshTokenRepository.delete(refreshToken);
            return createNewRefreshToken(user);
        }
    }

    @Override
    public UserDto getByRefreshToken(String token) {
        Optional<UserRefreshToken> existingRefreshToken = userRefreshTokenRepository.findByRefreshToken(token);

        if (existingRefreshToken.isEmpty()) {
            throw ApiException.notFound("User with refresh token " + token + " does not exist");
        }

        UserRefreshToken refreshToken = existingRefreshToken.get();

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            userRefreshTokenRepository.delete(refreshToken);
            throw ApiException.unauthorized("Refresh token expired");
        }

        return toDto(refreshToken.getUser());
    }

    private String createNewRefreshToken(User existingUser) {
        String refreshToken;
        refreshToken = UUID.randomUUID().toString();

        UserRefreshToken userRefreshToken = new UserRefreshToken(
                null,
                existingUser,
                refreshToken,
                LocalDateTime.now().plusHours(8));

        userRefreshTokenRepository.save(userRefreshToken);
        return refreshToken;
    }

    private UserDto toDto(User user) {
        return UserMapper.toDto(user);
    }
}

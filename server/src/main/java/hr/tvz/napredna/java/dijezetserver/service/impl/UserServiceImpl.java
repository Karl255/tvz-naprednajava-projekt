package hr.tvz.napredna.java.dijezetserver.service.impl;

import hr.tvz.napredna.java.dijezetserver.dto.UserDto;
import hr.tvz.napredna.java.dijezetserver.mapper.UserMapper;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.model.UserRole;
import hr.tvz.napredna.java.dijezetserver.repository.UserRepository;
import hr.tvz.napredna.java.dijezetserver.request.UserRequest;
import hr.tvz.napredna.java.dijezetserver.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public Optional<User> getByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDto getProfile(User user) {
        return toDto(user);
    }

    @Override
    public UserDto create(UserRequest userRequest) {
        Optional<User> existingUser = userRepository.findByUsername(userRequest.getUsername());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username " + userRequest.getUsername() + " already exists");
        }

        User newUser = UserMapper.toEntity(userRequest.getUsername(), passwordEncoder.encode(userRequest.getPassword()), UserRole.USER);

        return toDto(userRepository.save(newUser));
    }

    @Override
    public UserDto update(UserRequest userRequest) {
        Optional<User> existingUser = userRepository.findByUsername(userRequest.getUsername());

        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException("Username " + userRequest.getUsername() + " does not exist");
        }

        User newUser = existingUser.get();
        newUser.setHashedPassword(passwordEncoder.encode(userRequest.getPassword()));

        return toDto(userRepository.save(newUser));
    }

    @Override
    public void delete(Long id) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException("User with ID " + id + " does not exist");
        }

        userRepository.delete(existingUser.get());
    }

    private UserDto toDto(User user) {
        return UserMapper.toDto(user);
    }
}

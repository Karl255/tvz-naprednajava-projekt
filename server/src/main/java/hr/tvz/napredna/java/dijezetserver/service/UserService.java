package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.UserDto;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.request.UserRequest;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto getByUserName(String username);

    UserDto getProfile(User user);

    UserDto create(UserRequest userRequest);

    UserDto update(UserRequest userRequest);

    void delete(Long id);
}

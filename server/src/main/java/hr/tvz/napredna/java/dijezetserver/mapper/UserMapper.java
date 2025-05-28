package hr.tvz.napredna.java.dijezetserver.mapper;

import hr.tvz.napredna.java.dijezetserver.dto.UserDto;
import hr.tvz.napredna.java.dijezetserver.model.User;

public final class UserMapper {
    public UserMapper() {
    }

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername()
        );
    }
}

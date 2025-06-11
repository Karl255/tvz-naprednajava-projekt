package hr.tvz.napredna.java.dijezetserver.mapper;

import hr.tvz.napredna.java.dijezetserver.dto.UserDto;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.model.UserRole;

public final class UserMapper {
    public UserMapper() {
    }

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public static User toEntity(String username, String hashedPassword, UserRole type) {
        return new User(
                null,
                username,
                hashedPassword,
                type
        );
    }
}

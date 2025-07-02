package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.UserDto;

public interface UserRefreshTokenService {
    String getRefreshToken(String username);

    UserDto getByRefreshToken(String token);
}

package hr.tvz.napredna.java.dijezetserver.dto;

public record LoginDto(UserDto user, String refreshToken) {
}

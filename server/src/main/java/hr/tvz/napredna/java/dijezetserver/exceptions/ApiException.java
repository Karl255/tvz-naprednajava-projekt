package hr.tvz.napredna.java.dijezetserver.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public final class ApiException extends RuntimeException {
    private final List<String> messages;
    private final HttpStatus status;

    public ApiException(List<String> messages, HttpStatus status) {
        this.messages = messages;
        this.status = status;
    }

    public static ApiException notFound(String... messages) {
        return new ApiException(List.of(messages), HttpStatus.NOT_FOUND);
    }

    public static ApiException badRequest(String... messages) {
        return new ApiException(List.of(messages), HttpStatus.BAD_REQUEST);
    }

    public static ApiException unauthorized(String... messages) {
        return new ApiException(List.of(messages), HttpStatus.UNAUTHORIZED);
    }
}

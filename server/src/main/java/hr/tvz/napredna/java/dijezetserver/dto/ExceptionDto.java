package hr.tvz.napredna.java.dijezetserver.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ExceptionDto {
    private final List<String> messages;

    public ExceptionDto(List<String> messages) {
        this.messages = messages;
    }

    public ExceptionDto(String message) {
        this.messages = List.of(message);
    }

}

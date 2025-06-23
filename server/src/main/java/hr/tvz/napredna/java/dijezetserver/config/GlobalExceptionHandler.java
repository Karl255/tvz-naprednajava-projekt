package hr.tvz.napredna.java.dijezetserver.config;

import hr.tvz.napredna.java.dijezetserver.dto.ErrorDto;
import hr.tvz.napredna.java.dijezetserver.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex, WebRequest request) {
        ErrorDto error = new ErrorDto(List.of(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handleApiException(ApiException ex, WebRequest request) {
        ErrorDto error = new ErrorDto(ex.getMessages());
        return new ResponseEntity<>(error, ex.getStatus());
    }
}

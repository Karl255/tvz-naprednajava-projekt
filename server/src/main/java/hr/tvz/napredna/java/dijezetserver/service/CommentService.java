package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.CommentDto;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.request.CommentRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {
    List<CommentDto> findAll();

    List<CommentDto> findAllBefore(LocalDateTime timestamp);

    CommentDto create(CommentRequest commentRequest, User user);

    CommentDto update(Long id, CommentRequest commentRequest);

    void deleteById(Long id);

    void deleteByPinIds(List<Long> pinIds);
}

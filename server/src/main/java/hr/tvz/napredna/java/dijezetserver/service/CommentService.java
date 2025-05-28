package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.CommentDto;
import hr.tvz.napredna.java.dijezetserver.request.CommentRequest;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAll();

    CommentDto save(CommentRequest commentRequest);

    CommentDto update(Long id, CommentRequest commentRequest);

    void deleteById(Long id);
}

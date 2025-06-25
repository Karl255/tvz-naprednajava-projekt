package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.CommentDto;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.request.CommentRequest;
import hr.tvz.napredna.java.dijezetserver.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.COMMENT)
@Tag(name = "Comment", description = "CRUD operations for comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getComments() {
        return commentService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestBody CommentRequest commentRequest, @AuthenticationPrincipal User user) {
        return commentService.create(commentRequest, user);
    }

    @PutMapping("/{id}")
    public CommentDto updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        return commentService.update(id, commentRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
    }

}

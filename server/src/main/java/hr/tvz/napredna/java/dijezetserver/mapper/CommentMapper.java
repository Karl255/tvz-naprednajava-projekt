package hr.tvz.napredna.java.dijezetserver.mapper;

import hr.tvz.napredna.java.dijezetserver.dto.CommentDto;
import hr.tvz.napredna.java.dijezetserver.dto.PinDto;
import hr.tvz.napredna.java.dijezetserver.model.Comment;
import hr.tvz.napredna.java.dijezetserver.model.Pin;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.request.CommentRequest;

import java.time.LocalDateTime;
import java.util.List;

public final class CommentMapper {
    public CommentMapper() {
    }

    public static CommentDto toDto(Comment comment, PinDto pinDto, List<CommentDto> replies) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser() != null ? comment.getUser().getUsername() : null,
                pinDto,
                comment.getIssueType(),
                comment.getCreatedAt(),
                replies
        );
    }

    public static Comment toEntity(CommentRequest commentRequest, User user, Comment parentComment, Pin pin) {
        return new Comment(
                null,
                commentRequest.getContent(),
                user,
                pin,
                parentComment,
                List.of(),
                commentRequest.getIssueType(),
                LocalDateTime.now()
        );
    }
}

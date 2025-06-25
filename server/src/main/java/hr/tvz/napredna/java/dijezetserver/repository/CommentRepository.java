package hr.tvz.napredna.java.dijezetserver.repository;

import hr.tvz.napredna.java.dijezetserver.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByParentCommentIsNull();

    List<Comment> findByParentCommentIsNullAndCreatedAtIsBefore(LocalDateTime createdAt);

    void deleteAllByPinIdIn(List<Long> pinIds);
}

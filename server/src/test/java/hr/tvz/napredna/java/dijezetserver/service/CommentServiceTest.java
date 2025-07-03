package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.model.Comment;
import hr.tvz.napredna.java.dijezetserver.repository.CommentRepository;
import hr.tvz.napredna.java.dijezetserver.repository.PinRepository;
import hr.tvz.napredna.java.dijezetserver.request.CommentRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommentServiceTest extends BaseTest {
    private static final CommentRequest COMMENT_REQUEST = new CommentRequest(COMMENT.getContent(), COMMENT.getPin().getId(), COMMENT.getParentComment() != null ? COMMENT.getParentComment().getId() : null, COMMENT.getIssueType());
    private static final List<Long> PIN_IDS = List.of(1L, 2L, 3L);
    @Autowired
    private CommentService commentService;

    @MockitoBean
    private CommentRepository commentRepository;
    @MockitoBean
    private PinRepository pinRepository;

    @Test
    void shouldGetComments() {
        when(commentRepository.findByParentCommentIsNull()).thenReturn(COMMENTS);

        var comments = commentService.findAll();
        assertEquals(1, comments.size());
    }

    @Test
    void shouldSaveComment() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(COMMENT));
        when(pinRepository.findById(anyLong())).thenReturn(Optional.of(PIN));
        when(commentRepository.save(any())).thenReturn(COMMENT);
        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);

        commentService.create(COMMENT_REQUEST, USER);

        verify(commentRepository, times(1)).save(commentCaptor.capture());
        var comment = commentCaptor.getValue();
        assertEquals(COMMENT_REQUEST.getContent(), comment.getContent());
        assertEquals(COMMENT_REQUEST.getParentId(), comment.getParentComment() != null ? comment.getParentComment().getId() : null);
        assertEquals(COMMENT_REQUEST.getIssueType(), comment.getIssueType());
    }

    @Test
    void shouldUpdateComment() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(COMMENT));
        when(commentRepository.save(any())).thenReturn(COMMENT);
        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);

        commentService.update(COMMENT.getId(), COMMENT_REQUEST);

        verify(commentRepository, times(1)).save(commentCaptor.capture());
        var comment = commentCaptor.getValue();
        assertEquals(COMMENT_REQUEST.getContent(), comment.getContent());
        assertEquals(COMMENT_REQUEST.getIssueType(), comment.getIssueType());
    }

    @Test
    void shouldDeleteComment() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(COMMENT));
        doNothing().when(commentRepository).delete(any());

        commentService.deleteById(COMMENT.getId());
        verify(commentRepository, times(1)).delete(any());
    }

    @Test
    void shouldDeleteByPinId() {
        doNothing().when(commentRepository).deleteAllByPinIdIn(any());
        ArgumentCaptor<List<Long>> pinsIdCaptor = ArgumentCaptor.forClass(List.class);

        commentService.deleteByPinIds(PIN_IDS);

        verify(commentRepository, times(1)).deleteAllByPinIdIn(pinsIdCaptor.capture());
        assertEquals(PIN_IDS, pinsIdCaptor.getValue());
    }
}

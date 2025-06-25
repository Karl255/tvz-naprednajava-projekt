package hr.tvz.napredna.java.dijezetserver.service.impl;

import hr.tvz.napredna.java.dijezetserver.dto.CommentDto;
import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.dto.PinDto;
import hr.tvz.napredna.java.dijezetserver.dto.StationDto;
import hr.tvz.napredna.java.dijezetserver.exceptions.ApiException;
import hr.tvz.napredna.java.dijezetserver.mapper.CommentMapper;
import hr.tvz.napredna.java.dijezetserver.mapper.LineMapper;
import hr.tvz.napredna.java.dijezetserver.mapper.PinMapper;
import hr.tvz.napredna.java.dijezetserver.mapper.StationMapper;
import hr.tvz.napredna.java.dijezetserver.model.Comment;
import hr.tvz.napredna.java.dijezetserver.model.Line;
import hr.tvz.napredna.java.dijezetserver.model.Pin;
import hr.tvz.napredna.java.dijezetserver.model.Station;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.repository.CommentRepository;
import hr.tvz.napredna.java.dijezetserver.repository.PinRepository;
import hr.tvz.napredna.java.dijezetserver.request.CommentRequest;
import hr.tvz.napredna.java.dijezetserver.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PinRepository pinRepository;

    @Override
    public List<CommentDto> findAll() {
        return commentRepository.findByParentCommentIsNull().stream().map(this::toCommentDto).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findAllBefore(LocalDateTime timestamp) {
        return commentRepository.findByParentCommentIsNullAndCreatedAtIsBefore(timestamp).stream().map(this::toCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto create(CommentRequest commentRequest, User user) {
        Comment parent = Optional.ofNullable(commentRequest.getParentId()).map(id -> commentRepository.findById(id).orElseThrow(prepareParentCommentDoesNotExist())).orElse(null);
        Pin pin = pinRepository.findById(commentRequest.getPinId()).orElseThrow(preparePinDoesNotExist(commentRequest));

        Comment comment = toComment(commentRequest, user, parent, pin);

        return toCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto update(Long id, CommentRequest commentRequest) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(prepareCommentNotFoundException(id));

        existingComment.setContent(commentRequest.getContent());
        existingComment.setIssueType(commentRequest.getIssueType());

        return toCommentDto(commentRepository.save(existingComment));
    }

    @Override
    public void deleteById(Long id) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(prepareCommentNotFoundException(id));

        commentRepository.delete(existingComment);
    }

    @Override
    public void deleteByPinIds(List<Long> pinIds) {
        commentRepository.deleteAllByPinIdIn(pinIds);
    }

    private CommentDto toCommentDto(Comment comment) {
        Pin pin = comment.getPin();
        Station station = pin.getStation();
        Line line = pin.getLine();

        StationDto stationDto = StationMapper.toDto(station);
        LineDto lineDto = LineMapper.toDto(line);
        PinDto pinDto = PinMapper.toDto(pin, stationDto, lineDto);

        List<CommentDto> replies = comment.getReplies().stream().map(this::toCommentDto).toList();

        return CommentMapper.toDto(comment, pinDto, replies);
    }

    private Comment toComment(CommentRequest commentRequest, User user, Comment parentComment, Pin pin) {
        return CommentMapper.toEntity(commentRequest, user, parentComment, pin);
    }

    private static Supplier<ApiException> prepareCommentNotFoundException(Long id) {
        return () -> ApiException.notFound("Comment with id " + id + " not found");
    }

    private static Supplier<ApiException> preparePinDoesNotExist(CommentRequest commentRequest) {
        return () -> ApiException.notFound("Pin with id " + commentRequest.getPinId() + " not found");
    }

    private static Supplier<ApiException> prepareParentCommentDoesNotExist() {
        return () -> ApiException.notFound("Parent comment does not exist");
    }
}

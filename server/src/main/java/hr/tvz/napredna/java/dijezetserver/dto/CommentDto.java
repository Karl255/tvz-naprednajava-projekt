package hr.tvz.napredna.java.dijezetserver.dto;

import hr.tvz.napredna.java.dijezetserver.model.IssueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String user;
    private PinDto pin;
    private IssueType issueType;
    private LocalDateTime createdAt;
    private List<CommentDto> replies;
}

package hr.tvz.napredna.java.dijezetserver.request;

import hr.tvz.napredna.java.dijezetserver.model.IssueType;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private String content;
    private Long pinId;
    @Nullable private Long parentId;
    private IssueType issueType;
}

package hr.tvz.napredna.java.dijezetserver.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pin_id")
    private Pin pin;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    @Nullable
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    private IssueType issueType;
    private LocalDateTime createdAt;
}

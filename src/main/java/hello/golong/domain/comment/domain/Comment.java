package hello.golong.domain.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comment")
public class Comment {

    public void updateContent(String content) {
        this.content = content;
    }

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "comment_id") // nullable = false
    private Long id;

    @Column(name = "review_id")
    private Long reviewId;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "writer_id")
    private Long writerId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}

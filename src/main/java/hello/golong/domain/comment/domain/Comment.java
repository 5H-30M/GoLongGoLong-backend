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

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true) // nullable = false
    private Long comment_id;

    @Column
    private Long post_id;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column
    private Long writer_id;

    @Column
    private LocalDateTime created_at;

}

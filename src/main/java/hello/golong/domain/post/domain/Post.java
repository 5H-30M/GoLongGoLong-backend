package hello.golong.domain.post.domain;

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
@Table(name = "post")
public class Post {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true) // nullable = false
    private Long post_id;

    @Column(length = 30)
    private String title;

    private int status;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Long uploader_id;

    private LocalDateTime created_at;

    private Long period;

    @Column(length = 30)
    private String region;

}

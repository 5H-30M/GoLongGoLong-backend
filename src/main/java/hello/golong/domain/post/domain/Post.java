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

    public void updateStatus(int status) {
        this.status = status;
    }

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "post_id") // nullable = false
    private Long id;

    @Column(length = 30)
    private String title;

    private int status;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "uploader_id")
    private Long uploaderId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Long period;

    @Column(length = 30)
    private String region;

    @Column(name = "target_amount")
    private Long targetAmount;
}

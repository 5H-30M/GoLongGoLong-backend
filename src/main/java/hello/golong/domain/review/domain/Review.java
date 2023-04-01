package hello.golong.domain.review.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review")
public class Review {

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "review_id")
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Long amount;

    @Column(name = "raised_people")
    private Long raisedPeople;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

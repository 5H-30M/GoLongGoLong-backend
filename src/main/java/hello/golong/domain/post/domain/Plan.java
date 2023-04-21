package hello.golong.domain.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "plan")
public class Plan {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "plan_id")
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Long amount;
}

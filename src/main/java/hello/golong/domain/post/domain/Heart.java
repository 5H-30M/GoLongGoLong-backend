package hello.golong.domain.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "heart")
public class Heart {

    //TODO : DB에 HEART 테이블 추가하기
    @Id//pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "member_id")
    private Long memberId;

}

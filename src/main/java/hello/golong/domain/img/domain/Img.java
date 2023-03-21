package hello.golong.domain.img.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "img")
public class Img {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true) // nullable = false
    private Long img_id;

    private Long type;//모금게시글 사진(0), 모금후기 사진(1)

    private Long post_id;//게시글 아이디

    @Column(length = 30)
    private String file_name;//s3에 저장한 객체명

    private String img_url;//s3 객체 url

}

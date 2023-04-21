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

    public void updateImg(String fileName, String imgUrl) {
        this.fileName = fileName;
        this.imgUrl = imgUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "img_id") // nullable = false
    private Long id;

    private Long type;//모금게시글 사진(0), 모금후기 사진(1)

    @Column(name = "post_id")
    private Long postId;//게시글 아이디

    @Column(length = 100, name = "file_name")
    private String fileName;//s3에 저장한 객체명

    @Column(name = "img_url")
    private String imgUrl;//s3 객체 url

    @Column(name = "is_thumbnail")
    private int isThumbnail;

}

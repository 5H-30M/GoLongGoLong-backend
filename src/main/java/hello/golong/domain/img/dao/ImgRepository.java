package hello.golong.domain.img.dao;

import hello.golong.domain.img.domain.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {
    //thumbnail 아닌 이미지 찾기 위한 쿼리 메소드
    List<Img> findByPostIdAndType(Long postId, Long type);

}

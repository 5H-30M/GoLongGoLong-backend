package hello.golong.domain.img.dao;

import hello.golong.domain.img.domain.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {
    List<Img> findByPostIdAndType(Long postId, Long type);

}

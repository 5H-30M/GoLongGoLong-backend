package hello.golong.domain.img.dao;

import hello.golong.domain.img.domain.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImgRepository extends JpaRepository<Img, Long> {
    Optional<List<Img>> findByPostIdAndType(Long postId, Long type);

}

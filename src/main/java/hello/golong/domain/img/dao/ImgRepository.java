package hello.golong.domain.img.dao;

import hello.golong.domain.img.domain.Img;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgRepository extends JpaRepository<Img, Long> {
}

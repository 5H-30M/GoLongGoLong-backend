package hello.golong.domain.review.dao;

import hello.golong.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByPostId(Long post_id);
}

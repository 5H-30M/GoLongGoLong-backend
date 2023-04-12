package hello.golong.domain.post.dao;

import hello.golong.domain.post.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<List<Plan>> findByPostId(Long post_id);
}

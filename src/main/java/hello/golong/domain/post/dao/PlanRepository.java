package hello.golong.domain.post.dao;

import hello.golong.domain.post.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<List<Plan>> findByPostIdAndType(Long post_id, Long type);
}

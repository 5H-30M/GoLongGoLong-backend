package hello.golong.domain.post.dao;

import hello.golong.domain.post.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Long, Heart> {
    Optional<List<Heart>> findByMemberId(Long member_id);

    //TODO: 게시글에 하트수 표시할거면 필요함.
    //Optional<List<Heart>> findByPostId(Long post_id);
}

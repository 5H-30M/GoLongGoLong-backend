package hello.golong.domain.comment.dao;

import hello.golong.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewId(Long review_id);
    @Transactional
    void deleteByReviewId(Long review_id);
}

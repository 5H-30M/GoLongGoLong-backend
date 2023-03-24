package hello.golong.domain.comment.application;

import hello.golong.domain.comment.dao.CommentRepository;
import hello.golong.domain.comment.domain.Comment;
import hello.golong.domain.comment.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public CommentDto createComment(CommentDto commentDto) {

        commentDto.setCreated_at(LocalDateTime.now());
        Comment comment = Comment.builder()
                .post_id(commentDto.getPost_id())
                .writer_id(commentDto.getWriter_id())
                .content(commentDto.getContent())
                .created_at(commentDto.getCreated_at())
                .build();
        commentRepository.save(comment);

        commentDto.setComment_id(comment.getComment_id());
        return commentDto;
    }

    public void deleteComment(Long comment_id) {
        Optional<Comment> commentOptional = commentRepository.findById(comment_id);
        if(commentOptional.isPresent()) {
            commentRepository.deleteById(comment_id);
        }

        //TODO : IllegalArgumentException 처리해주는게 나은지..optional처리로 충분한지
        //TODO : Optional 예외처리 관련 수정하기

    }

}

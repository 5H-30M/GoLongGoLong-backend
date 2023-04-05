package hello.golong.domain.comment.application;

import hello.golong.domain.comment.dao.CommentRepository;
import hello.golong.domain.comment.domain.Comment;
import hello.golong.domain.comment.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentDto> findByReviewId(Long review_id) {
        Optional<List<Comment>> optionalComments = commentRepository.findByReviewId(review_id);
        List<CommentDto> commentDtos = new ArrayList<>();
        optionalComments.ifPresent(comments -> {

            for(Comment comment : comments) {
                CommentDto commentDto = CommentDto.builder()
                        .comment_id(comment.getId())
                        .review_id(comment.getReviewId())
                        .writer_id(comment.getWriterId())
                        .content(comment.getContent())
                        .created_at(comment.getCreatedAt())
                        .build();
                commentDtos.add(commentDto);
            }

        });


        return commentDtos;
    }

    public CommentDto createComment(CommentDto commentDto) {

        commentDto.setCreated_at(LocalDateTime.now());
        Comment comment = Comment.builder()
                .reviewId(commentDto.getReview_id())
                .writerId(commentDto.getWriter_id())
                .content(commentDto.getContent())
                .createdAt(commentDto.getCreated_at())
                .build();
        commentRepository.save(comment);

        commentDto.setComment_id(comment.getId());
        return commentDto;
    }

    public void deleteComment(Long comment_id) {
        Optional<Comment> optionalComment = commentRepository.findById(comment_id);
        optionalComment.ifPresent(comment -> {
            commentRepository.deleteById(comment_id);
        });

    }
    public void deleteByReviewId(Long review_id) {
        Optional<List<Comment>> optionalComments = commentRepository.findByReviewId(review_id);
        optionalComments.ifPresent(comments -> {
            commentRepository.deleteByReviewId(review_id);
        });

    }
    public CommentDto findComment(Long comment_id) {
        Optional<Comment> optionalComment = commentRepository.findById(comment_id);
        CommentDto commentDto = new CommentDto();
        optionalComment.ifPresent(comment -> {
            commentDto.setComment_id(comment_id);
            commentDto.setContent(comment.getContent());
            commentDto.setCreated_at(comment.getCreatedAt());
            commentDto.setReview_id(comment.getReviewId());
            commentDto.setWriter_id(comment.getWriterId());

        });
        return commentDto;
    }

    public void updateComment(Long comment_id, CommentDto commentDto) {
        Optional<Comment> optionalComment = commentRepository.findById(comment_id);
        optionalComment.ifPresent(comment -> {
            comment.updateContent(commentDto.getContent());
        });
    }

}

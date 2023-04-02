package hello.golong.domain.review.dto;

import hello.golong.domain.comment.domain.Comment;
import hello.golong.domain.comment.dto.CommentDto;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;

    private Long postId;

    private String content;

    private Long amount;

    private Long raisedPeople;

    private LocalDateTime createdAt;

    private List<String> images;

    private String receipt;

    private List<CommentDto> comments;
}

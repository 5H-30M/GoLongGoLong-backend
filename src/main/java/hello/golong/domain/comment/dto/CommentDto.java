package hello.golong.domain.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    private Long comment_id;

    private Long post_id;

    private String content;

    private Long writer_id;

    private LocalDateTime created_at;
}

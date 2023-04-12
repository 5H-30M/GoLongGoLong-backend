package hello.golong.domain.post.dto;

import hello.golong.domain.comment.dto.CommentDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private Long post_id;

    private String title;

    private int status;

    private String content;

    private Long uploader_id;

    private LocalDateTime created_at;

    private Long period;

    private String region;

    private Long target_amount;

    private Long raised_people;

    private Long amount;

    private List<String> images;

    private Map<String, Long> plans;
}

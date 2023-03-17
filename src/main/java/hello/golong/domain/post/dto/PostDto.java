package hello.golong.domain.post.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long post_id;

    private String title;

    private int status;

    private String content;

    private Long uploader_id;

    private LocalDateTime created_at;

    private Long period;

    private String region;
}

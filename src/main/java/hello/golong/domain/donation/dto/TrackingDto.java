package hello.golong.domain.donation.dto;

import hello.golong.domain.donation.domain.Donation;
import hello.golong.domain.post.dto.PostDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TrackingDto {

    private Long post_id;

    private String title;

    private int status;

    private Long uploader_id;

    private List<String> images;

    private DonationDto postTransaction;//post->구조자 트랜잭션

    private DonationDto myTransaction;//나의 기부 참여 트랜잭션



}

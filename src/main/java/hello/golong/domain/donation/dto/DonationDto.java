package hello.golong.domain.donation.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DonationDto {

    private Long id;

    private Long transactionId;

    private Long amount;

    private Long memberId;

    private Long postId;

}

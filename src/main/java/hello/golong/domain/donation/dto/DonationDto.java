package hello.golong.domain.donation.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DonationDto {

    private String transactionId;

    private Long amount;

    private Long memberId;

    private Long postId;

    private String privateKey;

    private String fromAddress;

    private String toAddress;

    private Date transactionCreatedAt;

}

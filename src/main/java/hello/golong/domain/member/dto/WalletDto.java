package hello.golong.domain.member.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WalletDto {

    String walletAddress;
    String privateKey;
}
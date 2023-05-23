package hello.golong.domain.member.dto;

import hello.golong.domain.post.dto.PostDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Data
public class MemberDto {

    //TODO : MemberDto 항목 점검하기
    private Long id;

    private String token;

    private String name;//kakaoNickname
    private String profileImgUrl;//kakaoProfileImg

    private String snsEmail;//kakaoEmail

    private Long GOLtokens;

    private String walletAddress;

    private String privateKey;

    private Boolean isVerified;

    private LocalDateTime createdAt;

    //TODO : dto에 userRole도 필요한지??


    private Long snsType;

    private Long snsProfile; // 사용자 유니크 ID 정보를 가져올 수 있음

    private List<PostDto> postsByMember;
}
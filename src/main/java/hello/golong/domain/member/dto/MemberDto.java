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
public class MemberDto {

    //TODO : MemberDto 항목 점검하기
    private Long id;

    private String name;

    private Long golongs;

    private String walletUrl;

    private String privateKey;

    private boolean isVerified;

    private LocalDateTime createdAt;

    private String profileImgUrl;

    private String snsEmail;

    private Long snsType;

    private Long snsProfile; // 사용자 유니크 ID 정보를 가져올 수 있음

    //TODO : 소셜로그인에서 저장해야할 정보인지 다시 확인하기
    private String accessToken;

    private List<PostDto> postsByMember;
}

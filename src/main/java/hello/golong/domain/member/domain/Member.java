package hello.golong.domain.member.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member")
public class Member {

    //TODO : 소셜로그인 관련해서 저장해야할 칼럼 수정하기

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    @Column(name = "golongs")
    private Long golongs;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name ="profile_img_url")
    private String profileImgUrl;

    @Column(name = "sns_email")
    private String snsEmail;

    @Column(name = "sns_type")
    private Long snsType;

    @Column(name = "sns_profile")
    private Long snsProfile; // 사용자 유니크 ID 정보를 가져올 수 있음

    //TODO : 소셜로그인에서 저장해야할 정보인지 다시 확인하기
    @Column(name = "access_token")
    private String accessToken;




}

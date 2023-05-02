package hello.golong.domain.member.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "member")
public class Member {

    //TODO : 소셜로그인 관련해서 저장해야할 칼럼 수정하기
    //TODO : UPDATE 가능한 항목 함수 만들기

    public void updateName(String name) {
        this.name = name;
    }

    public void updateGOLtokens(Long GOLtokens) {
        this.GOLtokens = GOLtokens;
    }

    public void updateIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    @Column(name = "GOLtokens")
    private Long GOLtokens;

    @Column(name = "wallet_url")
    private String walletUrl;

    //TODO : 개인키 암호화 여부 결정하기
    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "is_verified")
    private Boolean isVerified;

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

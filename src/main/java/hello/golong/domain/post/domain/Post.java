package hello.golong.domain.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post")
public class Post {

    public void updateStatus(int status) {
        this.status = status;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
    public void updateContent(String content) {
        this.content = content;
    }

    //해당 게시글의 모금 정보 업데이트
    //TODO : 기부 스마트 컨트랙트 성공시 Post 기부 정보 업데이트하기
    public void updateDonationInformation(Long new_amount) {
        this.amount += new_amount;
        this.raisedPeople++;
    }

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "post_id") // nullable = false
    private Long id;

    @Column(length = 30)
    private String title;

    private int status;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "uploader_id")
    private Long uploaderId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Long period;

    @Column(length = 30)
    private String region;

    @Column(name = "target_amount")
    private Long targetAmount;

    @Column(name = "raised_people")
    private Long raisedPeople;

    private Long amount;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "wallet_url")
    private String walletUrl;

    @Column(name = "transaction_id")
    private String transactionId;


}

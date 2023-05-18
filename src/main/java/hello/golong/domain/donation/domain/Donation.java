package hello.golong.domain.donation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "donation")
public class Donation {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "donation_id")
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    private Long amount;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "post_id")
    private Long postId;


}

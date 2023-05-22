
package hello.golong.domain.donation.dao;

import hello.golong.domain.donation.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    //Optional<Donation> findByPostIdAndMemberId(Long postId, Long memberId);
}

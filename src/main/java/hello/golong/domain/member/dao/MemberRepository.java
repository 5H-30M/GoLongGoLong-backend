package hello.golong.domain.member.dao;

import hello.golong.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySnsEmail(String sns_email);
}

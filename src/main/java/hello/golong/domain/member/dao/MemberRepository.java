package hello.golong.domain.member.dao;

import hello.golong.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findBySnsEmail(String snsEmail);
//    Member findByUserCode(Long userCode);

}
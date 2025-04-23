package programmers.nbe5_7_1_8bit.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmers.nbe5_7_1_8bit.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}

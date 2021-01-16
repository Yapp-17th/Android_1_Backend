package org.picon.auth.repository;

import org.picon.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByNickName(String nickName);

    Optional<Member> findByIdentity(String identity);

    Optional<Member> findByToken(String token);
}

package org.picon.repository;

import org.picon.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdentity(String identity);

    @Query("select m " +
            "from Member m " +
            "where " +
            "m.nickName like %:input% or " +
            "m.identity like %:input% ")
    List<Member> searchAll(@Param("input") String input);
}

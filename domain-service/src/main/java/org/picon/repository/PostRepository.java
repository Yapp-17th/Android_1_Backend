package org.picon.repository;

import org.picon.domain.Member;
import org.picon.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"member"})
    List<Post> findAllByMember(Member member);

    void deletePostByMemberAndId(Member member, Long id);

    @Query(value = "select p " +
            "from Post p " +
            "where p.createDate " +
            "between :startDate and :endDate " +
            "and p.member =:member")
    List<Post> findAllByMemberAndCreateMonth(@Param("member") Member member, @Param("startDate")LocalDate stateDate, @Param("endDate")LocalDate endDate);


}

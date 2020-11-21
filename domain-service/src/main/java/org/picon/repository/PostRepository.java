package org.picon.repository;

import org.picon.domain.Member;
import org.picon.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"member"})
    List<Post> findAllByMember(Member member);

    void deletePostByMemberAndId(Member member, Long id);
}

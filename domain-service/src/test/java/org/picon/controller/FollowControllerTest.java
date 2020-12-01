//package org.picon.controller;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.picon.domain.Follower;
//import org.picon.domain.Member;
//import org.picon.repository.MemberRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityNotFoundException;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//class FollowControllerTest {
//    @Autowired EntityManager entityManager;
//    @Autowired FollowController followController;
//    @Autowired MemberRepository memberRepository;
//
//    @Test
//    @Transactional
//    @Rollback
////    @Commit
//    @DisplayName("로그인 한 회원이 지정한 회원을 팔로우한다.")
//    void follow() {
//        long followMemberId = 1L;
//        String loginId = "id";
//        followController.follow(loginId, followMemberId);
//
//        entityManager.flush();
//        entityManager.clear();
//
//        Member loginMember = memberRepository.findByIdentity(loginId)
//                .orElseThrow(EntityNotFoundException::new);
//
//        Member followingMember = memberRepository.findById(followMemberId)
//                .orElseThrow(EntityNotFoundException::new);
//
//        List<Member> followings = loginMember.getFollowingMembers();
//        List<Member> followerMembers = followingMember.getFollowerMembers();
//
//        assertThat(followings).hasSize(1);
//        assertThat(followings.get(0)).isEqualTo(followingMember);
//
//        assertThat(followerMembers).hasSize(1);
//        assertThat(followerMembers.get(0)).isEqualTo(loginMember);
//    }
//
//}
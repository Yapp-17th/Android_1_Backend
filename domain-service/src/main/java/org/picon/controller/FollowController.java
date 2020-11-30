package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.picon.domain.Member;
import org.picon.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/domain/member/follow")
@Transactional(readOnly = true)
@Slf4j
public class FollowController {
    private final MemberRepository memberRepository;

    @PostMapping("/{id}")
    @Transactional
    public void follow(@RequestParam("identity") String identity, @PathVariable("id") Long followMemberId) {
        Member loginMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);

        Member followMember = memberRepository.findById(followMemberId)
                .orElseThrow(EntityNotFoundException::new);

        loginMember.following(followMember);
    }
}

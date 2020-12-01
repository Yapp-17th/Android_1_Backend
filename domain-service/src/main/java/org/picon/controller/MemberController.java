package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.*;
import org.picon.domain.Member;
import org.picon.dto.MemberDto;
import org.picon.dto.ProfileRequest;
import org.picon.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/domain/member")
@Transactional(readOnly = true)
@Slf4j
public class MemberController {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    @PostMapping("/profile")
    @Transactional
    public MemberDto uploadProfile(@RequestParam("identity")String identity, @RequestBody ProfileRequest profileRequest) {
        Member findMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        Member uploadProfile = Member.builder()
                .profileImageUrl(profileRequest.getProfileUrl())
                .role(findMember.getRole())
                .follower(findMember.getFollower())
                .followings(findMember.getFollowings())
                .identity(findMember.getIdentity())
                .password(findMember.getPassword())
                .nickName(findMember.getNickName())
                .id(findMember.getId())
                .build();
        return modelMapper.map(memberRepository.save(uploadProfile),MemberDto.class);
    }

    @DeleteMapping("/profile")
    @Transactional
    public Member deleteProfile(@RequestParam("identity")String identity) {
        Member findMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        Member uploadProfile = Member.builder()
                .profileImageUrl(null)
                .role(findMember.getRole())
                .follower(findMember.getFollower())
                .followings(findMember.getFollowings())
                .identity(findMember.getIdentity())
                .password(findMember.getPassword())
                .nickName(findMember.getNickName())
                .id(findMember.getId())
                .build();
        return memberRepository.save(uploadProfile);
    }

    @GetMapping("/search")
    public List<MemberDto> searchMembers(@RequestParam("input") String input) {
        List<Member> members = memberRepository.searchAll(input);
        return members.stream()
                .map(e -> modelMapper.map(e, MemberDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/")
    public MemberDto getMember(@RequestParam("identity") String identity) {
        Member findMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(findMember,MemberDto.class);
    }
}

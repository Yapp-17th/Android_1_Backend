package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.picon.domain.Member;
import org.picon.dto.MemberDto;
import org.picon.dto.ProfileRequest;
import org.picon.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    public MemberDto uploadProfile(@RequestParam("identity") String identity, @RequestBody ProfileRequest profileRequest) {
        Member findMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        Member uploadProfile = Member.builder()
                .profileImageUrl(profileRequest.getProfileUrl())
                .role(findMember.getRole())
                .followers(findMember.getFollowers())
                .followings(findMember.getFollowings())
                .identity(findMember.getIdentity())
                .password(findMember.getPassword())
                .nickName(findMember.getNickName())
                .id(findMember.getId())
                .build();
        return modelMapper.map(memberRepository.save(uploadProfile), MemberDto.class);
    }

    @DeleteMapping("/profile")
    @Transactional
    public Member deleteProfile(@RequestParam("identity") String identity) {
        Member findMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        Member uploadProfile = Member.builder()
                .profileImageUrl(null)
                .role(findMember.getRole())
                .followers(findMember.getFollowers())
                .followings(findMember.getFollowings())
                .identity(findMember.getIdentity())
                .password(findMember.getPassword())
                .nickName(findMember.getNickName())
                .id(findMember.getId())
                .build();
        return memberRepository.save(uploadProfile);
    }

    @GetMapping("/search")
    public List<MemberDto> searchMembers(@RequestParam("identity") String identity, @RequestParam("input") String input) {
        Member loginMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        List<Member> members = memberRepository.searchAll(input);
        List<MemberDto> memberDtos = members.stream()
                .map(member -> MemberDto.from(member, loginMember))
                .collect(Collectors.toList());
        return memberDtos;
    }

    @GetMapping("/")
    public MemberDto getMember(@RequestParam("identity") String identity) {
        Member findMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(findMember, MemberDto.class);
    }

    @GetMapping("/following")
    public List<MemberDto> getFollowings(@RequestParam("identity") String identity) {
        Member loginMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        List<Member> followingMembers = loginMember.getFollowingMembers();
        return followingMembers.stream()
                .map(member -> MemberDto.from(member, loginMember))
                .collect(Collectors.toList());
    }

    @GetMapping("/follower")
    public List<MemberDto> getFollowers(@RequestParam("identity") String identity) {
        Member loginMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        List<Member> followerMembers = loginMember.getFollowerMembers();
        return followerMembers.stream()
                .map(member -> MemberDto.from(member, loginMember))
                .collect(Collectors.toList());
    }


}

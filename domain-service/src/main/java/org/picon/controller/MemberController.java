package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.picon.domain.Member;
import org.picon.dto.*;
import org.picon.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
    public List<MemberDetailDto> searchMembers(@RequestParam("identity") String identity, @RequestParam("input") String input) {
        Member loginMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        List<Member> members = memberRepository.searchAll(input);
        List<MemberDetailDto> memberDetailDtos = new ArrayList<>();
        members.stream()
                .forEach(member -> {
                    int followers = member.getFollowerMembers().size();
                    int followings = member.getFollowingMembers().size();
                    memberDetailDtos.add(MemberDetailDto.builder()
                            .memberDto(MemberDto.from(member,loginMember))
                            .followInfo(FollowInfo.builder()
                                    .followings(followings)
                                    .followers(followers).build()).build());
                });
        return memberDetailDtos;
    }

    @GetMapping("/")
    public MemberDetailDto getMember(@RequestParam("identity") String identity) {
        Member findMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        MemberDto responseMemberDto = modelMapper.map(findMember,MemberDto.class);
        FollowInfo followInfo = FollowInfo.builder()
                .followers(findMember.getFollowerMembers().size())
                .followings(findMember.getFollowingMembers().size()).build();
        return MemberDetailDto.builder()
                .memberDto(responseMemberDto)
                .followInfo(followInfo)
                .build();

    }

    @GetMapping("/following")
    public MemberSearchResponse getFollowings(@RequestParam("identity") String identity) {
        Member loginMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        List<Member> followingMembers = loginMember.getFollowingMembers();
        List<MemberDto> memberDtos = followingMembers.stream()
                .map(member -> MemberDto.from(member, loginMember))
                .collect(Collectors.toList());

        return new MemberSearchResponse(memberDtos,FollowInfo.builder()
        .followers(0)
        .followings(followingMembers.size()).build());
    }

    @GetMapping("/follower")
    public MemberSearchResponse getFollowers(@RequestParam("identity") String identity) {
        Member loginMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        List<Member> followerMembers = loginMember.getFollowerMembers();
        List<MemberDto> memberDtos = followerMembers.stream()
                .map(member -> MemberDto.from(member, loginMember))
                .collect(Collectors.toList());

        return new MemberSearchResponse(memberDtos,FollowInfo.builder()
                .followers(followerMembers.size())
                .followings(0).build());
    }


}

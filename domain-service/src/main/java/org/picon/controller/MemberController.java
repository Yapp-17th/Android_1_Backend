package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.*;
import org.picon.domain.Member;
import org.picon.dto.MemberDto;
import org.picon.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return modelMapper.map(findMember, MemberDto.class);
    }
}

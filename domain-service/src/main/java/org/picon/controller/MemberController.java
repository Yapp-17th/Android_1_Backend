package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.*;
import org.picon.domain.Member;
import org.picon.dto.MemberDto;
import org.picon.repository.MemberRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/domain/member")
@Slf4j
public class MemberController {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public MemberDto getMember(@RequestParam("identity") String identity) {
        Member findMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(findMember, MemberDto.class);
    }
}

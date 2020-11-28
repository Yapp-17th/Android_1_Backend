package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.picon.dto.BaseResponse;
import org.picon.dto.member.MemberDto;
import org.picon.dto.member.MemberResponse;
import org.picon.dto.member.MemberSearchResponse;
import org.picon.jwt.JwtService;
import org.picon.service.FeignPostRemoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/display")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final FeignPostRemoteService feignPostRemoteService;
    private final JwtService jwtService;

    @GetMapping("/member/")
    public ResponseEntity<?> getMember(@RequestHeader("AccessToken") String accessToken) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        MemberDto memberDto = feignPostRemoteService.getMember(identityByToken);
        return ResponseEntity.ok().body(new MemberResponse(memberDto));
    }

    @GetMapping("/member/search")
    public ResponseEntity<?> searchMember(@RequestParam("input") String input) {
        List<MemberDto> memberDtos = feignPostRemoteService.searchMember(input);
        return ResponseEntity.ok().body(new MemberSearchResponse(memberDtos));
    }

    @PostMapping("/member/follow/{id}")
    public ResponseEntity<?> follow(@RequestHeader("AccessToken") String accessToken,
                                    @PathVariable("id") Long followingMemberId) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        feignPostRemoteService.follow(identityByToken, followingMemberId);
        return ResponseEntity.ok().body(new BaseResponse());
    }
}

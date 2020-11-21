package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.picon.dto.BaseResponse;
import org.picon.dto.MemberDto;
import org.picon.dto.member.MemberResponse;
import org.picon.dto.post.PostDto;
import org.picon.dto.post.PostResponse;
import org.picon.jwt.JwtService;
import org.picon.service.FeignPostRemoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

package org.picon.auth.controller;

import lombok.RequiredArgsConstructor;
import org.picon.auth.entity.Member;
import org.picon.auth.request.LogInRequest;
import org.picon.auth.request.SignInRequest;
import org.picon.auth.response.AccessTokenResponse;
import org.picon.auth.response.LogInResponse;
import org.picon.auth.response.SignInResponse;
import org.picon.auth.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signIn")
    public ResponseEntity signIn(@RequestBody @Valid SignInRequest signInRequest) {
        Member member = memberService.signIn(signInRequest);
        SignInResponse signInResponse = SignInResponse.builder()
                .id(member.getId())
                .identity(member.getIdentity())
                .nickName(member.getNickName())
                .build();
        return ResponseEntity.ok().body(signInResponse);
    }

    @PostMapping("/logIn")
    public ResponseEntity logIn(@RequestBody LogInRequest logInRequest) {
        LogInResponse logInResponse = memberService.logIn(logInRequest);
        return ResponseEntity.ok().body(logInResponse);
    }

    @GetMapping("/accessToken")
    public ResponseEntity getAccessToken(@RequestHeader String refreshToken) {
        AccessTokenResponse accessTokenResponse = memberService.getAccessToken(refreshToken);
        return ResponseEntity.ok().body(accessTokenResponse);
    }
}

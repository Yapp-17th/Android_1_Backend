package org.picon.auth.controller;

import lombok.RequiredArgsConstructor;
import org.picon.auth.entity.Member;
import org.picon.auth.request.LogInRequest;
import org.picon.auth.request.SignInRequest;
import org.picon.auth.response.AccessTokenResponse;
import org.picon.auth.response.LogInResponse;
import org.picon.auth.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.CREATED)
    public String signIn(@RequestBody SignInRequest signInRequest) {
        Member member = memberService.signIn(signInRequest);
        String email = member.getEmail();
        return "Welcome, " + email;
    }

    @PostMapping("/logIn")
    @ResponseStatus(HttpStatus.OK)
    public LogInResponse logIn(@RequestBody LogInRequest logInRequest) {
        return memberService.logIn(logInRequest);
    }

    @GetMapping("/accessToken")
    public AccessTokenResponse getAccessToken(@RequestHeader String refreshToken) {
        return memberService.getAccessToken(refreshToken);
    }

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test(@RequestBody SignInRequest signInRequest) {
        return memberService.test(signInRequest);
    }
}

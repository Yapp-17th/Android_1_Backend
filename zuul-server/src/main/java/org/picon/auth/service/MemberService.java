package org.picon.auth.service;

import lombok.RequiredArgsConstructor;
import org.picon.auth.entity.Member;
import org.picon.auth.exception.IdentityAlreadyExistException;
import org.picon.auth.exception.MemberNotFoundException;
import org.picon.auth.exception.NickNameAlreadyExistException;
import org.picon.auth.exception.PasswordMismatchException;
import org.picon.auth.repository.MemberRepository;
import org.picon.auth.request.LogInRequest;
import org.picon.auth.request.SignInRequest;
import org.picon.auth.response.AccessTokenResponse;
import org.picon.auth.response.LogInResponse;
import org.picon.global.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Member signIn(SignInRequest signInRequest) {
        ValidateSignIn(signInRequest.getIdentity(), signInRequest.getNickName());
        Member member = Member.builder()
                .identity(signInRequest.getIdentity())
                .password(passwordEncoder.encode(signInRequest.getPassword()))
                .nickName(signInRequest.getNickName())
                .token(signInRequest.getToken())
                .role("USER")
                .build();

        return memberRepository.save(member);
    }

    public LogInResponse logIn(LogInRequest logInRequest) {
        Member member = getMember(logInRequest);
        ValidatePassword(logInRequest.getPassword(), member.getPassword());
        String accessToken = jwtService.generateAccessTokenBy(member);
        String refreshToken = jwtService.generateRefreshToken(member);
        return LogInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Member getMember(LogInRequest logInRequest) {
        if (logInRequest.getToken() != null) {
            return memberRepository.findByToken(logInRequest.getToken())
                    .orElseThrow(EntityNotFoundException::new);
        }
        return memberRepository.findByIdentity(logInRequest.getIdentity()).orElseThrow(MemberNotFoundException::new);
    }

    public AccessTokenResponse getAccessToken(String refreshToken) {
        AccessTokenResponse accessTokenResponse = AccessTokenResponse.builder()
                .accessToken(jwtService.generateAccessTokenBy(refreshToken)).build();
        return accessTokenResponse;
    }

    public void ValidateSignIn(String identity, String nickName) {
        if (memberRepository.findByNickName(nickName).isPresent()) {
            throw new NickNameAlreadyExistException();
        } else if (memberRepository.findByIdentity(identity).isPresent()) {
            throw new IdentityAlreadyExistException();
        }
    }

    public void ValidatePassword(String logInPW, String memberPw) {
        if (!passwordEncoder.matches(logInPW, memberPw)) {
            throw new PasswordMismatchException();
        }
    }
}

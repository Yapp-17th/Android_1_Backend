package org.picon.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.picon.auth.entity.Member;
import org.picon.auth.repository.MemberRepository;
import org.picon.auth.request.LogInRequest;
import org.picon.auth.request.SignInRequest;
import org.picon.auth.response.AccessTokenResponse;
import org.picon.auth.response.LogInResponse;
import org.picon.auth.service.MemberService;
import org.picon.global.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

   @Autowired
    private MockMvc mockMvc;

    @Mock
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("예제 테스트")
    public void test() throws Exception {
        SignInRequest signInRequest = new SignInRequest("Email","Password","USER");
        given(memberService.test(signInRequest)).willReturn(signInRequest.getEmail());

        mockMvc.perform(
                post("/auth/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(signInRequest))
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("회원가입 성공")
    public void signInSuccess() throws Exception {
        SignInRequest signInRequest = new SignInRequest("Email","Password","USER");
        Member member = Member.builder()
                .role(signInRequest.getRole())
                .email(signInRequest.getEmail())
                .password(passwordEncoder.encode(signInRequest.getPassword())).build();

        given(memberService.signIn(signInRequest)).willReturn(member);

        mockMvc.perform(
                post("/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(signInRequest))
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    public Member signInForLogInTest() {
        SignInRequest signInRequest = new SignInRequest("Email","Password","USER");
        Member member = Member.builder()
                .role(signInRequest.getRole())
                .email(signInRequest.getEmail())
                .password(passwordEncoder.encode(signInRequest.getPassword())).build();
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("로그인 성공")
    public void logInSuccess() throws Exception{
       Member member = signInForLogInTest();
        LogInRequest logInRequest = new LogInRequest("Email", "Password");
        LogInResponse logInResponse = LogInResponse.builder()
                .accessToken(jwtService.generateAccessTokenBy(member))
                .refreshToken(jwtService.generateRefreshToken(member)).build();
        given(memberService.logIn(logInRequest)).willReturn(logInResponse);

        mockMvc.perform(
                post("/auth/logIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(logInRequest))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패 - 이메일 오류")
    public void logInFailMemberNotFoundException() throws Exception{
        Member member = signInForLogInTest();
        LogInRequest logInRequest = new LogInRequest("wrongEmail","Password");
        LogInResponse logInResponse = LogInResponse.builder()
                .accessToken(jwtService.generateAccessTokenBy(member))
                .refreshToken(jwtService.generateRefreshToken(member)).build();
        given(memberService.logIn(logInRequest)).willReturn(logInResponse);
        mockMvc.perform(
                post("/auth/logIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(logInRequest))
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 오류")
    public void logInFailPasswordMismatchException() throws Exception{
        Member member = signInForLogInTest();
        LogInRequest logInRequest = new LogInRequest("Email","WrongPassworddddddd");
        LogInResponse logInResponse = LogInResponse.builder()
                .accessToken(jwtService.generateAccessTokenBy(member))
                .refreshToken(jwtService.generateRefreshToken(member)).build();
        given(memberService.logIn(logInRequest)).willReturn(logInResponse);
        mockMvc.perform(
                post("/auth/logIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(logInRequest))
        )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
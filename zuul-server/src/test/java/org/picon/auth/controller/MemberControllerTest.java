package org.picon.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.picon.auth.request.SignInRequest;
import org.picon.auth.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    @Mock
    MemberService memberService;
    @Autowired
    private MockMvc mockMvc;

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
}
package org.picon.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.picon.auth.request.LogInRequest;
import org.picon.auth.request.SignInRequest;
import org.picon.auth.response.LogInResponse;
import org.picon.auth.response.SignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.picon.config.ApiDocumentUtils.getDocumentRequest;
import static org.picon.config.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class MemberControllerTest {

    protected static final String ROLE = "USER";
    protected static final String PW = "TestPassword";
    private static int testNO = 0;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원가입 성공")
    @Rollback
    @Test
    public void signInSuccess() throws Exception {
        SignInRequest signInRequest = createSignInRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest))
        );
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth-signIn",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(String.class).description("이메일"),
                                fieldWithPath("password").type(String.class).description("비밀번호"),
                                fieldWithPath("role").type(String.class).description("권한")
                        )
                ));

    }

    protected SignInRequest createSignInRequest() {
        testNO++;

        return new SignInRequest(
                "testEmail" + testNO,
                PW,
                ROLE
        );
    }

    public SignInResponse createUser() throws Exception {
        SignInRequest signInRequest = createSignInRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc.perform(
                post("/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(signInRequest))
        ).andReturn();

        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                SignInResponse.class
        );
    }

    public LogInResponse createLogInResponse() throws Exception {
        SignInResponse signInResponse = createUser();
        LogInRequest logInRequest = new LogInRequest(signInResponse.getEmail(), PW);
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc.perform(
                post("/auth/logIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(logInRequest))
        ).andReturn();

        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                LogInResponse.class
        );
    }

    @DisplayName("로그인 성공")
    @Rollback
    @Test
    public void logInSuccess() throws Exception {
        SignInResponse signInResponse = createUser();
        LogInRequest logInRequest = new LogInRequest(signInResponse.getEmail(), PW);
        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/auth/logIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(logInRequest))
        );
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth-logIn",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(String.class).description("이메일"),
                                fieldWithPath("password").type(String.class).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("status").type(Integer.class).description("응답상태 코드"),
                                fieldWithPath("errors").type(String.class).description("상세 에러 메세지"),
                                fieldWithPath("errorCode").type(String.class).description("에러 코드"),
                                fieldWithPath("errorMessage").type(String.class).description("에러 메세지"),
                                fieldWithPath("accessToken").type(String.class).description("accessToken"),
                                fieldWithPath("refreshToken").type(String.class).description("refreshToken")
                        )
                ));
    }

    @DisplayName("엑세스 토큰 요청 성공")
    @Rollback
    @Test
    public void requestAccessTokenSuccess() throws Exception {
        LogInResponse logInResponse = createLogInResponse();
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/auth/accessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("refreshToken", logInResponse.getRefreshToken())
        );
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth-refreshAccessToken",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("refreshToken").description("refreshToken")
                        ),
                        responseFields(
                                fieldWithPath("status").type(Integer.class).description("응답상태 코드"),
                                fieldWithPath("errors").type(String.class).description("상세 에러 메세지"),
                                fieldWithPath("errorCode").type(String.class).description("에러 코드"),
                                fieldWithPath("errorMessage").type(String.class).description("에러 메세지"),
                                fieldWithPath("accessToken").type(String.class).description("새로 생성된 accessToken")
                        )
                ));
    }
}
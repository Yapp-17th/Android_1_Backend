package org.picon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.picon.config.RestDocsConfiguration;
import org.picon.dto.BaseResponse;
import org.picon.dto.member.MemberDto;
import org.picon.dto.member.MemberResponse;
import org.picon.dto.member.ProfileRequest;
import org.picon.dto.post.PostRequest;
import org.picon.jwt.JwtService;
import org.picon.service.FeignPostRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class})
@Import(RestDocsConfiguration.class)
class MemberControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    FeignPostRemoteService feignPostRemoteService;

    @MockBean
    JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {
        // NOTE: 콘솔에서 한글 깨지는거 보고 싶을 때 사용. 응답 json pretty 안해줘서 주석처리
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation))
//                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
//                .alwaysDo(print())
//                .build();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 추가
    }

    @Test
    @DisplayName("회원 프로필 사진을 업로드한다")
    @Rollback
    void uploadProfileSuccess() throws Exception {
        ProfileRequest profileRequest = new ProfileRequest("profileURL");
        MemberDto memberDto = new MemberDto(1L, "id", "nickname", "role", LocalDate.now() ,"ProfileURL");
        given(jwtService.findIdentityByToken(any())).willReturn("id");
        given(feignPostRemoteService.UploadProfile("id",profileRequest)).willReturn(memberDto);

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/display/member/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "accessToken Example")
                        .content(objectMapper.writeValueAsString(profileRequest))
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("member-uploadProfile",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("www.yappandone17.shop")
                                                .removePort(),
                                        prettyPrint()
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header"),
                                        headerWithName("AccessToken").description("로그인으로 얻어온 토큰 값")
                                ),
                                relaxedRequestFields(
                                        fieldWithPath("profileUrl").type(String.class).description("프로필 이미지 url")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("member").type(MemberDto.class).description("회원 정보"),
                                        fieldWithPath("member.identity").type(String.class).description("아이디"),
                                        fieldWithPath("member.nickName").type(String.class).description("닉네임"),
                                        fieldWithPath("member.role").type(String.class).description("역할"),
                                        fieldWithPath("member.createdDate").type(LocalDate.class).description("회원가입 날짜"),
                                        fieldWithPath("member.profileImageUrl").type(String.class).description("프로필 이미지 url")
                                )
                        )
                );
    }

    @Test
    @DisplayName("회원 프로필 사진을 삭제한다")
    @Rollback
    void deleteProfileSuccess() throws Exception {

        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/display/member/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "accessToken Example")
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("member-deleteProfile",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("www.yappandone17.shop")
                                                .removePort(),
                                        prettyPrint()
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header"),
                                        headerWithName("AccessToken").description("로그인으로 얻어온 토큰 값")
                                ),
                                responseFields(
                                        fieldWithPath("status").type(Integer.class).description("응답상태 코드"),
                                        fieldWithPath("errors").type(String.class).description("상세 에러 메세지"),
                                        fieldWithPath("errorCode").type(String.class).description("에러 코드"),
                                        fieldWithPath("errorMessage").type(String.class).description("에러 메세지")
                                )
                        )
                );
    }

    @Test
    @DisplayName("회원 정보를 얻어온다.")
    @Rollback
    public void getMemberTest() throws Exception {
        MemberDto memberDto = new MemberDto(1L, "id", "nickname", "role", LocalDate.now() ,"image_url");
        given(feignPostRemoteService.getMember(any())).willReturn(memberDto);
        given(jwtService.findIdentityByToken(any())).willReturn("id");

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/display/member/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "accessToken Example")
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("posts[].id", is(notNullValue())))
                .andDo(
                        document("member-get",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("www.yappandone17.shop")
                                                .removePort(),
                                        prettyPrint()
                                ),
//                                pathParameters(
//                                        parameterWithName("X").description("없음")
//                                ),
//                                requestParameters(
//                                        parameterWithName("X").description("쿼리 파라미터 없음")
//                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header"),
                                        headerWithName("AccessToken").description("로그인으로 얻어온 토큰 값")
                                ),
//                                requestFields(
//                                        fieldWithPath("X").description("요청 본문 없음")
//                                ),
                                relaxedResponseFields(
                                        fieldWithPath("member").type(MemberDto.class).description("회원 정보"),
                                        fieldWithPath("member.identity").type(String.class).description("아이디"),
                                        fieldWithPath("member.nickName").type(String.class).description("닉네임"),
                                        fieldWithPath("member.role").type(String.class).description("역할"),
                                        fieldWithPath("member.createdDate").type(LocalDate.class).description("회원가입 날짜"),
                                        fieldWithPath("member.profileImageUrl").type(String.class).description("프로필 이미지 사진")
                                )
                        )
                );
    }

    @Test
    @DisplayName("회원 정보를 검색한다.")
    @Rollback
    public void searchMemberTest() throws Exception {
        MemberDto memberDto = new MemberDto(1L, "id", "nickname", "role", LocalDate.now() ,"image_url");
        given(feignPostRemoteService.searchMember(any())).willReturn(Collections.singletonList(memberDto));
        given(jwtService.findIdentityByToken(any())).willReturn("id");

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/display/member/search")
                        .param("input", "input")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "accessToken Example")
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("posts[].id", is(notNullValue())))
                .andDo(
                        document("member-search",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("www.yappandone17.shop")
                                                .removePort(),
                                        prettyPrint()
                                ),
                                requestParameters(
                                        parameterWithName("input").description("검색어")
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header"),
                                        headerWithName("AccessToken").description("로그인으로 얻어온 토큰 값")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("members[]").type(List.class).description("회원 정보"),
                                        fieldWithPath("members[].id").type(MemberDto.class).description("회원 식별자"),
                                        fieldWithPath("members[].identity").type(String.class).description("아이디"),
                                        fieldWithPath("members[].nickName").type(String.class).description("닉네임"),
                                        fieldWithPath("members[].role").type(String.class).description("역할"),
                                        fieldWithPath("members[].createdDate").type(LocalDate.class).description("회원가입 날짜"),
                                        fieldWithPath("members[].profileImageUrl").type(String.class).description("프로필 이미지 사진")
                                )
                        )
                );
    }


    @Test
    @DisplayName("회원을 팔로잉한다.")
    @Rollback
    public void followingTest() throws Exception {
        given(jwtService.findIdentityByToken(any())).willReturn("id");

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/display/member/follow/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "accessToken Example")
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("posts[].id", is(notNullValue())))
                .andDo(
                        document("follow",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("www.yappandone17.shop")
                                                .removePort(),
                                        prettyPrint()
                                ),
                                pathParameters(
                                        parameterWithName("id").description("팔로잉하는 회원 식별자")
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header"),
                                        headerWithName("AccessToken").description("로그인으로 얻어온 토큰 값")
                                )
                        )
                );
    }


}
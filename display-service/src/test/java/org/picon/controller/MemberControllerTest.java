package org.picon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.picon.config.RestDocsConfiguration;
import org.picon.dto.member.*;
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
        MemberDto memberDto = new MemberDto(1L, "id", "nickname", "role", LocalDate.now(), "ProfileURL", false);
        given(jwtService.findIdentityByToken(any())).willReturn("id");
        given(feignPostRemoteService.uploadProfile("id", profileRequest)).willReturn(memberDto);

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/display/member/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "{{authToken}}")
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
                        .header("AccessToken", "{{authToken}}")
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
    @DisplayName("로그인 한 회원 정보를 얻어온다.")
    @Rollback
    public void getMemberTest() throws Exception {
        MemberDto memberDto = new MemberDto(1L, "id", "nickname", "role", LocalDate.now(), "image_url", false);
        FollowInfo followInfo = new FollowInfo(0,3);
        given(feignPostRemoteService.getMember(any())).willReturn(MemberDetailDto.builder()
                .memberDto(memberDto).followInfo(followInfo).build());
        given(jwtService.findIdentityByToken(any())).willReturn("id");

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/display/member/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "{{authToken}}")
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
                                        fieldWithPath("memberDetailDto").type(MemberDetailDto.class).description("회원 상세 정보"),
                                        fieldWithPath("memberDetailDto.memberDto").type(MemberDto.class).description("회원 정보"),
                                        fieldWithPath("memberDetailDto.memberDto.identity").type(String.class).description("아이디"),
                                        fieldWithPath("memberDetailDto.memberDto.nickName").type(String.class).description("닉네임"),
                                        fieldWithPath("memberDetailDto.memberDto.role").type(String.class).description("역할"),
                                        fieldWithPath("memberDetailDto.memberDto.createdDate").type(LocalDate.class).description("회원가입 날짜"),
                                        fieldWithPath("memberDetailDto.memberDto.profileImageUrl").type(String.class).description("프로필 이미지 사진"),
                                        fieldWithPath("memberDetailDto.memberDto.isFollowing").type(Boolean.class).description("무시하세요"),
                                        fieldWithPath("memberDetailDto.followInfo").type(FollowInfo.class).description("회원기준 팔로잉/팔로워 정보"),
                                        fieldWithPath("memberDetailDto.followInfo.followers").type(FollowInfo.class).description("팔로수워 수"),
                                        fieldWithPath("memberDetailDto.followInfo.followings").type(FollowInfo.class).description("팔로잉 수")
                                        )
                        )
                );
    }

    @Test
    @DisplayName("회원 정보를 검색한다.")
    @Rollback
    public void searchMemberTest() throws Exception {
        MemberDto memberDto = new MemberDto(1L, "id", "nickname", "role", LocalDate.now(), "image_url", false);
        FollowInfo followInfo = new FollowInfo(0,3);
        given(feignPostRemoteService.searchMember(any(), any())).willReturn(Collections.singletonList(new MemberDetailDto(memberDto,followInfo)) );
        given(jwtService.findIdentityByToken(any())).willReturn("id");

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/display/member/search")
                        .param("input", "input")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "{{authToken}}")
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
                                        fieldWithPath("memberDetailDtos[]").type(List.class).description("회원 정보리스트"),
                                        fieldWithPath("memberDetailDtos[]").type(MemberDetailDto.class).description("회원 정보"),
                                        fieldWithPath("memberDetailDtos[].memberDto").type(MemberDto.class).description("회원 정보"),
                                        fieldWithPath("memberDetailDtos[].memberDto.identity").type(String.class).description("아이디"),
                                        fieldWithPath("memberDetailDtos[].memberDto.nickName").type(String.class).description("닉네임"),
                                        fieldWithPath("memberDetailDtos[].memberDto.role").type(String.class).description("역할"),
                                        fieldWithPath("memberDetailDtos[].memberDto.createdDate").type(LocalDate.class).description("회원가입 날짜"),
                                        fieldWithPath("memberDetailDtos[].memberDto.profileImageUrl").type(String.class).description("프로필 이미지 사진"),
                                        fieldWithPath("memberDetailDtos[].memberDto.isFollowing").type(Boolean.class).description("무시하세요"),
                                        fieldWithPath("memberDetailDtos[].followInfo").type(FollowInfo.class).description("회원기준 팔로잉/팔로워 정보"),
                                        fieldWithPath("memberDetailDtos[].followInfo.followers").type(FollowInfo.class).description("팔로수워 수"),
                                        fieldWithPath("memberDetailDtos[].followInfo.followings").type(FollowInfo.class).description("팔로잉 수")
                                )
                        )
                );
    }


    @Test
    @DisplayName("회원을 팔로우한다.")
    @Rollback
    public void followingTest() throws Exception {
        given(jwtService.findIdentityByToken(any())).willReturn("id");

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/display/member/follow/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "{{authToken}}")
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

    @Test
    @DisplayName("회원을 언팔로우한다.")
    @Rollback
    public void unfollowingTest() throws Exception {
        given(jwtService.findIdentityByToken(any())).willReturn("id");

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/display/member/unfollow/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "{{authToken}}")
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("posts[].id", is(notNullValue())))
                .andDo(
                        document("unfollow",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("www.yappandone17.shop")
                                                .removePort(),
                                        prettyPrint()
                                ),
                                pathParameters(
                                        parameterWithName("id").description("언팔로우하는 회원 식별자")
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header"),
                                        headerWithName("AccessToken").description("로그인으로 얻어온 토큰 값")
                                )
                        )
                );
    }


    @Test
    @DisplayName("팔로잉 리스트를 조회한다.")
    @Rollback
    public void getFollowingsTest() throws Exception {
        MemberDto memberDto = new MemberDto(1L, "id", "nickname", "role", LocalDate.now(), "image_url", false);
        FollowInfo followInfo = new FollowInfo(0,3);
       MemberSearchResponse m = MemberSearchResponse.builder().members(Collections.singletonList(memberDto))
                .followInfo(followInfo).build();
        given(feignPostRemoteService.getFollowingMembers(any())).willReturn(m);
        given(jwtService.findIdentityByToken(any())).willReturn("id");

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/display/member/following")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "{{authToken}}")
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("posts[].id", is(notNullValue())))
                .andDo(
                        document("following",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("www.yappandone17.shop")
                                                .removePort(),
                                        prettyPrint()
                                ),
//                                requestParameters(
//                                        parameterWithName("input").description("검색어")
//                                ),
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
                                        fieldWithPath("members[].profileImageUrl").type(String.class).description("프로필 이미지 사진"),
                                        fieldWithPath("members[].isFollowing").type(Boolean.class).description("팔로우하는 회원인지 여부"),
                                        fieldWithPath("followInfo.followers").type(FollowInfo.class).description("무시해주세요"),
                                        fieldWithPath("followInfo.followings").type(FollowInfo.class).description("팔로잉 수")
                                )
                        )
                );
    }

    @Test
    @DisplayName("팔로워 리스트를 조회한다.")
    @Rollback
    public void getFollowersTest() throws Exception {
        MemberDto memberDto = new MemberDto(1L, "id", "nickname", "role", LocalDate.now(), "image_url", false);
        FollowInfo followInfo = new FollowInfo(0,3);
        MemberSearchResponse m = MemberSearchResponse.builder().members(Collections.singletonList(memberDto))
                .followInfo(followInfo).build();
        given(feignPostRemoteService.getFollowerMembers(any())).willReturn(m);
        given(jwtService.findIdentityByToken(any())).willReturn("id");

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/display/member/follower")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "{{authToken}}")
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("posts[].id", is(notNullValue())))
                .andDo(
                        document("follower",
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
                                relaxedResponseFields(
                                        fieldWithPath("members[]").type(List.class).description("회원 정보"),
                                        fieldWithPath("members[].id").type(MemberDto.class).description("회원 식별자"),
                                        fieldWithPath("members[].identity").type(String.class).description("아이디"),
                                        fieldWithPath("members[].nickName").type(String.class).description("닉네임"),
                                        fieldWithPath("members[].role").type(String.class).description("역할"),
                                        fieldWithPath("members[].createdDate").type(LocalDate.class).description("회원가입 날짜"),
                                        fieldWithPath("members[].profileImageUrl").type(String.class).description("프로필 이미지 사진"),
                                        fieldWithPath("members[].isFollowing").type(Boolean.class).description("팔로우하는 회원인지 여부"),
                                        fieldWithPath("followInfo.followers").type(FollowInfo.class).description("팔로워 수"),
                                        fieldWithPath("followInfo.followings").type(FollowInfo.class).description("무시해주세요")
                                )
                        )
                );
    }


}
package org.picon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.picon.config.RestDocsConfiguration;
import org.picon.dto.post.*;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class})
@Import(RestDocsConfiguration.class)
class PostDtoControllerTest {
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
    @DisplayName("게시글 정보를 얻어온다.")
    public void getPostTest() throws Exception {
        given(feignPostRemoteService.readPostsByMember(any())).willReturn(Collections.singletonList(getExpectedPostDto()));
        given(jwtService.findEmailByToken(any())).willReturn("email");

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/displays/post/", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "accessToken Example")
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("posts[].id", is(notNullValue())))
                .andDo(
                        document("post-read",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("www.yappandone17.shop")
                                                .removePort(),
                                        prettyPrint()
                                ),
//                                pathParameters(
//                                        parameterWithName("id").description("조회하고 싶은 게시글의 번호")
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
                                        fieldWithPath("posts[]").type(List.class).description("게시글 정보 리스트"),
                                        fieldWithPath("posts[].id").type(Integer.class).description("게시글 식별자"),
                                        fieldWithPath("posts[].coordinate").type(CoordinateDto.class).description("게시글이 가진 좌표,주소 객체"),
                                        fieldWithPath("posts[].coordinate.lat").type(String.class).description("위도"),
                                        fieldWithPath("posts[].coordinate.lng").type(String.class).description("경도"),
                                        fieldWithPath("posts[].address").type(AddressDto.class).description("게시글이 가진 주소 객체"),
                                        fieldWithPath("posts[].address.address").type(String.class).description("주소명").optional(),
                                        fieldWithPath("posts[].address.addrCity").type(String.class).description("시").optional(),
                                        fieldWithPath("posts[].address.addrDo").type(String.class).description("도").optional(),
                                        fieldWithPath("posts[].address.addrGu").type(String.class).description("구").optional(),
                                        fieldWithPath("posts[].emotion").type(Emotion.class).description("감정").optional(),
                                        fieldWithPath("posts[].memo").type(String.class).description("메모").optional()
                                )
                        )
                );
    }

    private PostDto getExpectedPostDto() {
        CoordinateDto coordinateDto = new CoordinateDto(BigDecimal.valueOf(1.1), BigDecimal.valueOf(1.1));
        AddressDto addressDto = new AddressDto("주소", "시", "도", "구");
        return PostDto.builder()
                .id(1L)
                .coordinate(coordinateDto)
                .address(addressDto)
                .emotion(Emotion.BLUE_GRAY)
                .imageUrls(Arrays.asList("url1", "url2"))
                .memo("memo")
                .build();
    }

    @Test
    @DisplayName("게시글을 생성한다.")
    void createPostTest() throws Exception {
        given(feignPostRemoteService.createPost(any(), any())).willReturn(getExpectedPostDto());
        given(jwtService.findEmailByToken(any())).willReturn("email");

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/displays/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "accessToken Example")
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(new PostRequest(getExpectedPostDto())))
        )
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andDo(
                        document("post-create",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("www.yappandone17.shop")
                                                .removePort(),
                                        prettyPrint()
                                ),
//                                pathParameters(
//                                        parameterWithName("id").description("조회하고 싶은 게시글의 번호")
//                                ),
//                                requestParameters(
//                                        parameterWithName("X").description("쿼리 파라미터 없음")
//                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header"),
                                        headerWithName("AccessToken").description("로그인으로 얻어온 토큰 값")
                                ),
                                requestFields(
                                        fieldWithPath("post").type(PostDto.class).description("게시글 정보"),
                                        fieldWithPath("post.id").type(Long.class).description("게시글 식별자"),
                                        fieldWithPath("post.coordinate").type(CoordinateDto.class).description("게시글이 가진 좌표,주소 객체"),
                                        fieldWithPath("post.coordinate.lat").type(BigDecimal.class).description("위도"),
                                        fieldWithPath("post.coordinate.lng").type(BigDecimal.class).description("경도"),
                                        fieldWithPath("post.imageUrls").type(JsonFieldType.ARRAY).description("이미지 url String").optional(),
                                        fieldWithPath("post.address").type(AddressDto.class).description("게시글이 가진 주소 객체"),
                                        fieldWithPath("post.address.address").type(JsonFieldType.STRING).description("주소명").optional(),
                                        fieldWithPath("post.address.addrCity").type(JsonFieldType.STRING).description("시").optional(),
                                        fieldWithPath("post.address.addrDo").type(JsonFieldType.STRING).description("도").optional(),
                                        fieldWithPath("post.address.addrGu").type(JsonFieldType.STRING).description("구").optional(),
                                        fieldWithPath("post.emotion").type(Emotion.class).description("감정").optional(),
                                        fieldWithPath("post.memo").type(JsonFieldType.STRING).description("메모").optional()
                                )
                        )
                );
    }

    @Test
    @DisplayName("게시글 생성 시 좌표값이 없으면 status 400을 리턴한다.(http status 200)")
    void createTestExceptionTest() throws Exception {
        given(feignPostRemoteService.createPost(any(), any())).willReturn(getExpectedPostDto());
        given(jwtService.findEmailByToken(any())).willReturn("email");

//        String requestPostStr = "{\"post\":{\"id\":1,\"coordinate\":{\"lat\":null,\"lng\":null},\"address\":{\"address\":\"주소\",\"addrCity\":\"시\",\"addrDo\":\"도\",\"addrGu\":\"구\"},\"emotion\":\"BLUE_GRAY\",\"memo\":\"memo\"}}";
        String requestPostStr = objectMapper.writeValueAsString(new PostRequest(getExpectedPostDto().toBuilder().address(null).build()));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/displays/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "accessToken Example")
                        .characterEncoding("utf-8")
                        .content(requestPostStr)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(400)));
    }
}
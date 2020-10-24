package org.picon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.picon.config.RestDocsConfiguration;
import org.picon.dto.Address;
import org.picon.dto.Coordinate;
import org.picon.dto.Emotion;
import org.picon.dto.Post;
import org.picon.service.FeignPostRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
class PostControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    FeignPostRemoteService feignPostRemoteService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 추가
    }

    @Test
    @DisplayName("게시글 정보를 얻어온다.")
    public void getPostTest() throws Exception {
        given(feignPostRemoteService.getPostInfo(any())).willReturn(getExpectedReturnValue());

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/displays/post/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andDo(
                        document("post-read",
                                pathParameters(
                                        parameterWithName("id").description("조회하고 싶은 게시글의 번호")
                                ),
//                                requestParameters(
//                                        parameterWithName("X").description("쿼리 파라미터 없음")
//                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header")
                                ),
//                                requestFields(
//                                        fieldWithPath("X").description("요청 본문 없음")
//                                ),
                                relaxedResponseFields(
                                        fieldWithPath("id").type(Integer.class).description("게시글 식별자"),
                                        fieldWithPath("coordinate").type(Coordinate.class).description("게시글이 가진 좌표,주소 객체"),
                                        fieldWithPath("coordinate.lat").type(String.class).description("위도"),
                                        fieldWithPath("coordinate.lng").type(String.class).description("경도"),
                                        fieldWithPath("address").type(Address.class).description("게시글이 가진 주소 객체"),
                                        fieldWithPath("address.address").type(String.class).description("주소명").optional(),
                                        fieldWithPath("address.addrCity").type(String.class).description("시").optional(),
                                        fieldWithPath("address.addrDo").type(String.class).description("도").optional(),
                                        fieldWithPath("address.addrGu").type(String.class).description("구").optional(),
                                        fieldWithPath("emotion").type(Emotion.class).description("감정").optional(),
                                        fieldWithPath("memo").type(String.class).description("메모").optional(),
                                        fieldWithPath("createDate").type(LocalDate.class).description("생성 날짜").optional()
                                )
                        )
                );
    }

    private Post getExpectedReturnValue() {
        Coordinate coordinate = new Coordinate(1.1d, 1.1d);
        Address address = new Address("주소", "시", "도", "구");
        Post post = new Post(1L, coordinate, address, Emotion.BLUE_GRAY, "memo", LocalDate.of(2020, 1, 1));
        return post;
    }

    @Test
    @DisplayName("게시글을 생성한다.")
    void createPostTest() throws Exception {
        given(feignPostRemoteService.createPost(any())).willReturn(getExpectedReturnValue());

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/displays/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(getExpectedReturnValue()))
        )
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andDo(
                        document("post-create",
//                                pathParameters(
//                                        parameterWithName("id").description("조회하고 싶은 게시글의 번호")
//                                ),
//                                requestParameters(
//                                        parameterWithName("X").description("쿼리 파라미터 없음")
//                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header")
                                ),
                                requestFields(
                                        fieldWithPath("id").type(Integer.class).description("게시글 식별자"),
                                        fieldWithPath("coordinate").type(Coordinate.class).description("게시글이 가진 좌표,주소 객체"),
                                        fieldWithPath("coordinate.lat").type(String.class).description("위도"),
                                        fieldWithPath("coordinate.lng").type(String.class).description("경도"),
                                        fieldWithPath("address").type(Address.class).description("게시글이 가진 주소 객체"),
                                        fieldWithPath("address.address").type(String.class).description("주소명").optional(),
                                        fieldWithPath("address.addrCity").type(String.class).description("시").optional(),
                                        fieldWithPath("address.addrDo").type(String.class).description("도").optional(),
                                        fieldWithPath("address.addrGu").type(String.class).description("구").optional(),
                                        fieldWithPath("emotion").type(Emotion.class).description("감정").optional(),
                                        fieldWithPath("memo").type(String.class).description("메모").optional(),
                                        fieldWithPath("createDate").type(LocalDate.class).description("생성 날짜").optional()
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("id").type(Integer.class).description("게시글 식별자"),
                                        fieldWithPath("coordinate").type(Coordinate.class).description("게시글이 가진 좌표,주소 객체"),
                                        fieldWithPath("coordinate.lat").type(String.class).description("위도"),
                                        fieldWithPath("coordinate.lng").type(String.class).description("경도"),
                                        fieldWithPath("address").type(Address.class).description("게시글이 가진 주소 객체"),
                                        fieldWithPath("address.address").type(String.class).description("주소명"),
                                        fieldWithPath("address.addrCity").type(String.class).description("시"),
                                        fieldWithPath("address.addrDo").type(String.class).description("도"),
                                        fieldWithPath("address.addrGu").type(String.class).description("구"),
                                        fieldWithPath("emotion").type(Emotion.class).description("감정"),
                                        fieldWithPath("memo").type(String.class).description("메모"),
                                        fieldWithPath("createDate").type(LocalDate.class).description("생성 날짜")
                                )
                        )
                );
    }
}
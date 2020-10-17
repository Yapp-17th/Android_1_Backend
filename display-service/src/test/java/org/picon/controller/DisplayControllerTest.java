package org.picon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.picon.dto.Address;
import org.picon.dto.Coordinate;
import org.picon.dto.Post;
import org.picon.service.FeignPostRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @see <a href="https://github.com/ePages-de/restdocs-wiremock/blob/master/server/src/test/java/com/example/notes/ApiDocumentation.java">참고 링크</a>
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-sources/snippets")
//@WebAppConfiguration
public class DisplayControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    FeignPostRemoteService feignPostRemoteService;

    // ========== <mockMvc Setting> ==========

    //    @Rule
//    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

//    private RestDocumentationResultHandler documentationHandler;
//
//    @Autowired
//    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

//    @Before
//    public void each() {
//        this.documentationHandler = document("{method-name}",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()),
//                wiremockJson()
//        );
//
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
//                .apply(documentationConfiguration(this.restDocumentation))
//                .alwaysDo(this.documentationHandler)
//                .build();
//    }

    @Test
    public void restDocsTest() throws Exception {
        given(feignPostRemoteService.getPostInfo(any())).willReturn(getExpectedReturnValue());

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/displays/posts/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andDo(
                        document("",
//                                wiremockJson(idFieldReplacedWithPathParameterValue()),
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
                                        fieldWithPath("coordinate").type(Object.class).description("게시글이 가진 좌표,주소 객체"),
                                        fieldWithPath("coordinate.lat").type(String.class).description("위도"),
                                        fieldWithPath("coordinate.lng").type(String.class).description("경도"),
                                        fieldWithPath("address").type(Object.class).description("게시글이 가진 주소 객체"),
                                        fieldWithPath("address.address").type(Object.class).description("주소명"),
                                        fieldWithPath("address.addrCity").type(Object.class).description("시"),
                                        fieldWithPath("address.addrDo").type(Object.class).description("도"),
                                        fieldWithPath("address.addrGu").type(Object.class).description("구"),
                                        fieldWithPath("createDate").type(Object.class).description("생성 날짜")
                                )
                        )
                );
    }

    private String getExpectedReturnValue() throws JsonProcessingException {
        Coordinate coordinate = new Coordinate(1.1d, 1.1d);
        Address address = new Address("주소", "시", "도", "구");
        Post post = new Post(1L, coordinate, address, LocalDate.of(2020, 1, 1));
        return objectMapper.writeValueAsString(post);
    }
}
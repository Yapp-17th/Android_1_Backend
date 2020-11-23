package org.picon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.picon.config.RestDocsConfiguration;
import org.picon.dto.post.Emotion;
import org.picon.dto.statics.AddressCount;
import org.picon.dto.statics.EmotionCount;
import org.picon.dto.statics.StatisticsDto;
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
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class})
@Import(RestDocsConfiguration.class)
class StatisticsControllerTest {

    @MockBean
    FeignPostRemoteService feignPostRemoteService;

    @MockBean
    JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("감정별, 지역별 게시글 통계값 가져오기 성공")
    public void getPostCountsByStatisticsSuccess() throws Exception {

        List<EmotionCount> emotionCounts = new ArrayList<>();
        emotionCounts.add(new EmotionCount(Emotion.CORN_FLOWER,3));
        emotionCounts.add(new EmotionCount(Emotion.BLUE_GRAY,2));
        List<AddressCount> addressCounts = new ArrayList<>();
        addressCounts.add(new AddressCount("서울시","성북구",emotionCounts,5));
        addressCounts.add(new AddressCount("서울시","강남구",emotionCounts,5));

        given(jwtService.findIdentityByToken(any())).willReturn("identity");
        given(feignPostRemoteService.getPostsByStatistics(2020,11,"identity")).willReturn(new StatisticsDto(emotionCounts,5,addressCounts));

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/display/statistics/{year}/{month}", 2020,11)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AccessToken", "accessToken Example")
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("statistics",
                                preprocessRequest(modifyUris()
                                                .scheme("http")
                                                .host("www.yappandone17.shop")
                                                .removePort(),
                                        prettyPrint()
                                ),
                                pathParameters(
                                        parameterWithName("year").description("조회하고싶은 게시글 년도"),
                                        parameterWithName("month").description("조회하고싶은 게시글 월")
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header"),
                                        headerWithName("AccessToken").description("로그인으로 얻어온 토큰 값")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("emotionCounts[]").type(List.class).description("감정별 게시글 TOP5"),
                                        fieldWithPath("emotionCounts[].emotion").type(Emotion.class).description("감정"),
                                        fieldWithPath("emotionCounts[].count").type(Integer.class).description("감정에 대한 게시글 갯수"),
                                        fieldWithPath("emotionTotal").type(Integer.class).description("감정별 게시글 총 갯수"),
                                        fieldWithPath("addressCounts[]").type(List.class).description("장소별 게시글 TOP5"),
                                        fieldWithPath("addressCounts[].addrCity").type(String.class).description("장소 - 시"),
                                        fieldWithPath("addressCounts[].addrGu").type(String.class).description("장소 - 구"),
                                        fieldWithPath("addressCounts[].emotionCounts[]").type(List.class).description("해당 장소의 게시글들에 대한 감정별 게시글"),
                                        fieldWithPath("addressCounts[].emotionCounts[].emotion").type(Emotion.class).description("감정"),
                                        fieldWithPath("addressCounts[].emotionCounts[].count").type(Integer.class).description("감정에 대한 게시글 갯수"),
                                        fieldWithPath("addressCounts[].total").type(Integer.class).description("장소별 게시글 총 갯수")
                                        )
                        )
                );
    }

}
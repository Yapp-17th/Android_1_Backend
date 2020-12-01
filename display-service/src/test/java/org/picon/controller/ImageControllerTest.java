package org.picon.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.picon.jwt.JwtService;
import org.picon.service.FeignPostRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileDescriptor;
import java.util.List;

import static org.mockito.Mockito.description;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FeignPostRemoteService feignPostRemoteService;

    @MockBean
    PostController postController;

    @MockBean
    JwtService jwtService;

    @DisplayName("S3에 이미지 업로드")
    @Test
    public void uploadImageSuccess() throws Exception {
        MockMultipartFile firstImage = new MockMultipartFile("firstImage", "firstImage.jpg", "multipart/form-data", "sampleImage".getBytes());
        MockMultipartFile secondImage = new MockMultipartFile("secondImage", "secondImage.png", "multipart/form-data", "sampleImage2".getBytes());

        ResultActions resultActions = mockMvc.perform(fileUpload("/display/post/images")
                .file(firstImage)
                .file(secondImage)
        );

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-uploadImages",
                        preprocessRequest(modifyUris()
                                        .scheme("http")
                                        .host("www.yappandone17.shop")
                                        .removePort(),
                                prettyPrint()
                        ),
                        responseFields(
                                fieldWithPath("[]").type(List.class).description("이미지 url 리스트")
                        )
                ));

    }

    @DisplayName("S3에 프로필 이미지 업로드")
    @Test
    public void uploadProfileImageSuccess() throws Exception {
        MockMultipartFile firstImage = new MockMultipartFile("firstImage", "firstImage.jpg", "multipart/form-data", "sampleImage".getBytes());

        mockMvc.perform(multipart("/display/member/image").file("image","thisIsImage".getBytes()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member-uploadImage",
                        preprocessRequest(modifyUris()
                                        .scheme("http")
                                        .host("www.yappandone17.shop")
                                        .removePort(),
                                prettyPrint()
                        )
                ));
    }

}
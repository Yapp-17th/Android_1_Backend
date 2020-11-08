//package org.picon.controller;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.picon.domain.Post;
//import org.picon.dto.AddressDto;
//import org.picon.dto.CoordinateDto;
//import org.picon.dto.Emotion;
//import org.picon.dto.PostDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class PostControllerTest {
//    @Autowired
//    private ModelMapper modelMapper;
//
//
//    @Test
//    @DisplayName("PostDto <-> Post 변환 테스트")
//    void mappingPostDtoTest() {
//        CoordinateDto coordinateDto = new CoordinateDto(BigDecimal.valueOf(1, 1), BigDecimal.valueOf(1.2));
//        AddressDto addressDto = new AddressDto("address", null, null, null);
//        PostDto postDto = PostDto.builder()
//                .id(null)
//                .coordinate(coordinateDto)
//                .address(addressDto)
//                .imageUrls(Arrays.asList("url1", "url2"))
//                .emotion(Emotion.BLUE_GRAY)
//                .memo("memo")
//                .build();
//
//        Post mappedPost = modelMapper.map(postDto, Post.class);
//        System.out.println(mappedPost);
//    }
//
//
//}
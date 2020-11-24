package org.picon.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.picon.domain.*;
import org.picon.dto.AddressDto;
import org.picon.dto.CoordinateDto;
import org.picon.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
class PostControllerTest {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    @DisplayName("PostDto -> Post 변환 테스트")
    void mappingPostDtoTest() {
        CoordinateDto coordinateDto = new CoordinateDto(BigDecimal.valueOf(1, 1), BigDecimal.valueOf(1.2));
        AddressDto addressDto = new AddressDto("address", null, null, null);
        PostDto postDto = PostDto.builder()
                .id(null)
                .coordinate(coordinateDto)
                .address(addressDto)
                .imageUrls(Arrays.asList("url1", "url2"))
                .emotion(Emotion.BLUE_GRAY)
                .memo("memo")
                .build();

        Post mappedPost = modelMapper.map(postDto, Post.class);
        System.out.println(mappedPost);
    }

    @Test
    @DisplayName("Post to PostDto 변환 테스트")
    void mappingPostToPostDtoTest() {
        Coordinate coordinate = Coordinate.builder()
                .lat(BigDecimal.valueOf(12.12d))
                .lng(BigDecimal.valueOf(12.12d))
                .build();
        Address address = Address.builder()
                .address("address")
                .addrCity("addrCity")
                .addrDo("addrDo")
                .addrGu("addrGu")
                .build();
        Member member = Member.builder()
                .id(1L)
                .identity("identity")
                .nickName("nickName")
                .password("password")
                .role("role")
                .profileImageUrl("profileImageUrl")
                .build();
        Post post = Post.builder()
                .id(1L)
                .coordinate(coordinate)
                .address(address)
                .imageUrls(Collections.singletonList("123.url"))
                .emotion(Emotion.BLUE_GRAY)
                .member(member)
                .memo("memo")
                .build();
        modelMapper.createTypeMap(Post.class, PostDto.class)
                .addMapping(e -> e.getMember().getProfileImageUrl(), PostDto::setProfileImageUrl);
        PostDto map = modelMapper.map(post, PostDto.class);
        System.out.println(map);
    }
}

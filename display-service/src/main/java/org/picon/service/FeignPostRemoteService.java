package org.picon.service;

import org.picon.dto.member.MemberDto;
import org.picon.dto.post.PostDto;
import org.picon.dto.statics.StatisticsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@FeignClient(name = "domain", fallbackFactory = FeignPostRemoteServiceFallbackFactory.class)
public interface FeignPostRemoteService {
    @GetMapping(path = "/domain/post/")
    List<PostDto> readPostsByMember(@RequestParam("identity") String identity);

    @PostMapping(path = "/domain/post/")
    PostDto createPost(@RequestBody PostDto post, @RequestParam("identity") String identity);

    @PostMapping(path = "/domain/post/images", consumes = MULTIPART_FORM_DATA_VALUE)
    List<String> ImagesUpload(@RequestPart("images") MultipartFile[] multipartFiles);

    @DeleteMapping(path = "/domain/post/{id}")
    ResponseEntity deletePost(@PathVariable("id") Long id, @RequestParam("identity") String identity);

    @GetMapping(path = "/domain/statistics/{year}/{month}")
    StatisticsDto getPostsByStatistics(@PathVariable("year")int year, @PathVariable("month")int month, @RequestParam("identity")String identity);

    @GetMapping(path = "/domain/member/")
    MemberDto getMember(@RequestParam("identity") String identity);

    @GetMapping(path = "/domain/member/search")
    List<MemberDto> searchMember(@RequestParam("input") String input);

    @PostMapping(path = "/domain/member/follow/{id}")
    void follow(@RequestParam("identity") String identity, @PathVariable("id") Long followMemberId);
}


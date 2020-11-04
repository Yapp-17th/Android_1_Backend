package org.picon.service;

import org.picon.dto.post.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@FeignClient(name = "domain", fallbackFactory = FeignPostRemoteServiceFallbackFactory.class)
public interface FeignPostRemoteService {
    @GetMapping(path = "/domain/post/")
    List<PostDto> readPostsByMember(@RequestParam("email") String email);

    @PostMapping(path = "/domain/post/")
    PostDto createPost(@RequestBody PostDto post, @RequestParam("email") String email);

    @PostMapping(path = "/domain/post/images", consumes = MULTIPART_FORM_DATA_VALUE)
    List<String> ImagesUpload(@RequestPart("images") MultipartFile[] multipartFiles);

}


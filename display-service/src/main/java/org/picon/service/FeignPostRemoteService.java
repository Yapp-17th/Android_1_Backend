package org.picon.service;

import org.picon.config.MultipartSupportConfig;
import org.picon.dto.post.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@FeignClient(name = "domain", fallbackFactory = FeignPostRemoteServiceFallbackFactory.class, configuration = MultipartSupportConfig.class)
public interface FeignPostRemoteService {
    @GetMapping(path = "/post/{postId}")
    PostDto readPost(@PathVariable("postId") Long postId);

    @PostMapping(path = "/post/")
    PostDto createPost(@RequestBody PostDto post);

    @PostMapping(path = "/post/images", consumes = MULTIPART_FORM_DATA_VALUE)
    List<String> ImagesUpload(@RequestPart("images") MultipartFile[] multipartFiles);

}


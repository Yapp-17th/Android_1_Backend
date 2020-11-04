package org.picon.service;

import org.picon.dto.post.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "domain", fallbackFactory = FeignPostRemoteServiceFallbackFactory.class)
public interface FeignPostRemoteService {
    @GetMapping(path = "/post/{postId}")
    PostDto readPost(@PathVariable("postId") Long postId);

    @PostMapping(path = "/post/")
    PostDto createPost(@RequestBody PostDto post);
}

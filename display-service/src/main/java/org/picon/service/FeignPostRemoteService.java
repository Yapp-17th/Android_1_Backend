package org.picon.service;

import org.picon.dto.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "domain", fallbackFactory = FeignPostRemoteServiceFallbackFactory.class)
public interface FeignPostRemoteService {
    @GetMapping(path = "/post/{postId}")
    Post getPostInfo(@PathVariable("postId") Long postId);

    @PostMapping(path = "/post/")
    Post createPost(Post post);
}

package org.picon.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.RequiredArgsConstructor;
import org.picon.dto.Post;
import org.picon.service.FeignPostRemoteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/displays")
@RequiredArgsConstructor
public class PostController {
    private final FeignPostRemoteService feignPostRemoteService;

    @GetMapping("/post/{id}")
    public Post getPost(@PathVariable Long id) {
        return feignPostRemoteService.getPostInfo(id);
    }

    @PostMapping("/post")
    public Post createPost(Post post) {
        return feignPostRemoteService.createPost(post);
    }
}

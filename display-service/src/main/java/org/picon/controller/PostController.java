package org.picon.controller;

import lombok.RequiredArgsConstructor;

import org.picon.dto.PostDto;
import org.picon.service.FeignPostRemoteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/displays")
@RequiredArgsConstructor
public class PostController {
    private final FeignPostRemoteService feignPostRemoteService;

    @GetMapping("/post/{id}")
    public PostDto getPost(@PathVariable Long id) {
        return feignPostRemoteService.readPost(id);
    }

    @PostMapping("/post")
    public PostDto createPost(@RequestBody PostDto post) {
        return feignPostRemoteService.createPost(post);
    }
}

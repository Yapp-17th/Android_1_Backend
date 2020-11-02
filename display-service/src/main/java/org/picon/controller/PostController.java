package org.picon.controller;

import lombok.RequiredArgsConstructor;
import org.picon.dto.post.PostDto;
import org.picon.dto.post.PostRequest;
import org.picon.dto.post.PostResponse;
import org.picon.service.FeignPostRemoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/displays")
@RequiredArgsConstructor
public class PostController {
    private final FeignPostRemoteService feignPostRemoteService;

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        PostDto postDto = feignPostRemoteService.readPost(id);
        PostResponse postResponse = new PostResponse(postDto);
        return ResponseEntity.ok().body(postResponse);
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        PostDto postDto = feignPostRemoteService.createPost(postRequest.getPost());
        return ResponseEntity.ok().body(postDto);
    }
}

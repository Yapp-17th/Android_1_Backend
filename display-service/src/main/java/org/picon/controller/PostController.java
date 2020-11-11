package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.picon.dto.BaseResponse;
import org.picon.dto.post.PostDto;
import org.picon.dto.post.PostRequest;
import org.picon.dto.post.PostResponse;
import org.picon.jwt.JwtService;
import org.picon.service.FeignPostRemoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/display")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final FeignPostRemoteService feignPostRemoteService;
    private final JwtService jwtService;

    @GetMapping("/post/")
    public ResponseEntity<?> getPosts(@RequestHeader("AccessToken") String accessToken) {
        String emailByToken = jwtService.findEmailByToken(accessToken);
        List<PostDto> postDtos = feignPostRemoteService.readPostsByMember(emailByToken);
        return ResponseEntity.ok().body(new PostResponse(postDtos));
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostRequest postRequest, @RequestHeader("AccessToken") String accessToken) {
        String emailByToken = jwtService.findEmailByToken(accessToken);
        PostDto postDto = feignPostRemoteService.createPost(postRequest.getPost(), emailByToken);
        return ResponseEntity.ok().body(new BaseResponse());
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity deletePost(@RequestHeader("AccessToken")String accessToken, @PathVariable Long id) {
        String emailByToken = jwtService.findEmailByToken(accessToken);
        feignPostRemoteService.deletePost(id,emailByToken);
        return ResponseEntity.ok().body(new BaseResponse());
    }
}

package org.picon.controller;

import lombok.RequiredArgsConstructor;
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
public class PostController {
    private final FeignPostRemoteService feignPostRemoteService;
    private final JwtService jwtService;

    @GetMapping("/post/")
    public ResponseEntity<?> getPosts(@RequestHeader("AccessToken") String accessToken) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        List<PostDto> postDtos = feignPostRemoteService.readPostsByLoginId(identityByToken);
        return ResponseEntity.ok().body(new PostResponse(postDtos));
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostRequest postRequest, @RequestHeader("AccessToken") String accessToken) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        PostDto postDto = feignPostRemoteService.createPost(postRequest.getPost(), identityByToken);
        return ResponseEntity.ok().body(new BaseResponse());
    }

    @GetMapping("/post/member/{id}")
    public ResponseEntity<?> getPostsOfMember(@RequestHeader("AccessToken") String accessToken, @PathVariable Long id) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        List<PostDto> postDtos = feignPostRemoteService.readPostsById(id);
        return ResponseEntity.ok().body(new PostResponse(postDtos));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@RequestHeader("AccessToken") String accessToken, @PathVariable("id") Long id) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        feignPostRemoteService.deletePost(id, identityByToken);
        return ResponseEntity.ok().body(new BaseResponse());
    }
}

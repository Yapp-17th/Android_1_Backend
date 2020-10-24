package org.picon.controller;

import lombok.extern.slf4j.Slf4j;
import org.picon.domain.Post;
import org.picon.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {
    private final PostRepository postRepository = new PostRepository();

    @GetMapping(path = "/{id}")
    public Post getPost(@PathVariable Long id) {
        Post postById = postRepository.getPostById(id);
        System.out.println(postById);
        return postById;
    }

    @PostMapping(path = "/")
    public Post createPost(Post post) {
        log.info("========== CREATE POST ========== ");
        log.info("{}", post);
        log.info("========== END OF CREATE POST ========== ");
        return post;
    }

    @GetMapping("/test")
    public String test(@RequestHeader String accessToken) {
        return "this is domain service api!!!!"+accessToken;
    }
}

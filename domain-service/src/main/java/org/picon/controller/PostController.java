package org.picon.controller;

import org.picon.domain.Post;
import org.picon.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository = new PostRepository();

    @GetMapping(path = "{postId}")
    public Post getPost(@PathVariable Long postId) {
        Post postById = postRepository.getPostById(postId);
        System.out.println(postById);
        return postById;
    }
}

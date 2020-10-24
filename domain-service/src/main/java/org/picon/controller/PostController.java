package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.picon.domain.Address;
import org.picon.domain.Coordinate;
import org.picon.domain.Emotion;
import org.picon.domain.Post;
import org.picon.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
@Transactional(readOnly = true)
public class PostController {
    private final PostRepository postRepository;

    @GetMapping(path = "/{id}")
    public Post readPost(@PathVariable Long id) {
        log.info("========== READ POST ========== ");
        Post findPost = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        log.info("========== END OF READ POST ========== ");
        return findPost;
    }

    @PostMapping(path = "/")
    @Transactional
    public Post createPost(@RequestBody org.picon.dto.Post postDto) {
        log.info("========== CREATE POST ========== ");
        Coordinate coordinate = new Coordinate(postDto.getCoordinate().getLat(), postDto.getCoordinate().getLng());
        Address address = new Address(
                postDto.getAddress().getAddress(),
                postDto.getAddress().getAddrCity(),
                postDto.getAddress().getAddrDo(),
                postDto.getAddress().getAddrGu()
        );
        Emotion emotion = Emotion.valueOf(postDto.getEmotion().name());
        Post save = postRepository.save(Post.of(coordinate, address, emotion, postDto.getMemo()));
        log.info("========== END OF CREATE POST ========== ");
        return save;
    }

    @GetMapping("/test")
    public String test(@RequestHeader String accessToken) {
        return "this is domain service api!!!!"+accessToken;
    }
}

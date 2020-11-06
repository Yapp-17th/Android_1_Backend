package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.picon.domain.*;
import org.picon.dto.AddressDto;
import org.picon.dto.CoordinateDto;
import org.picon.dto.PostDto;
import org.picon.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        CoordinateDto coordinateDto = CoordinateDto.coordinateDtoOf(findPost.getCoordinate());
        AddressDto addressDto = AddressDto.AddressDtoOf(findPost.getAddress());
        List<String> imageUrls = new ArrayList<>();
        findPost.getImages().stream().forEach(image -> {
            imageUrls.add(image.getImageUrl());
        });
        return findPost;
    }

    @PostMapping(path = "/")
    @Transactional
    public Post createPost(@RequestBody PostDto postDto) {
        log.info("========== CREATE POST ========== ");
        Coordinate coordinate = new Coordinate(postDto.getCoordinateDto().getLat(), postDto.getCoordinateDto().getLng());
        Address address = new Address(
                postDto.getAddressDto().getAddress(),
                postDto.getAddressDto().getAddrCity(),
                postDto.getAddressDto().getAddrDo(),
                postDto.getAddressDto().getAddrGu()
        );
        Emotion emotion = Emotion.valueOf(postDto.getEmotion().name());
        Set<Image> images = new HashSet<>();
        postDto.getImageUrls().stream().forEach(image -> {
            images.add(new Image(image));
        });
        Post save = postRepository.save(Post.of(coordinate, address, emotion, images, postDto.getMemo()));
        log.info("========== END OF CREATE POST ========== ");
        return save;
    }
}

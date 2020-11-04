package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.picon.domain.*;
import org.picon.dto.AddressDto;
import org.picon.dto.CoordinateDto;
import org.picon.dto.PostDto;
import org.picon.repository.MemberRepository;
import org.picon.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.SecurityContext;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/domain/post")
@Slf4j
@Transactional(readOnly = true)
public class PostController {
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @GetMapping(path = "/")
    public List<PostDto> readPostsByMember(@RequestParam("email") String email) {
        log.info("========== READ POST ========== ");
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        List<Post> findPosts = postRepository.findAllByMember(member);
        List<PostDto> collect = findPosts.stream()
                .map(e -> modelMapper.map(e, PostDto.class))
                .collect(Collectors.toList());
        log.info("========== END OF READ POST ========== ");
        return collect;
    }

    @PostMapping(path = "/")
    @Transactional
    public Post createPost(@RequestBody PostDto postDto, @RequestParam("email") String email) {
        log.info("========== CREATE POST ========== ");
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Post post = modelMapper.map(postDto, Post.class);
        post.setMember(member);
        Post save = postRepository.save(post);
        log.info("========== END OF CREATE POST ========== ");
        return save;
    }
}

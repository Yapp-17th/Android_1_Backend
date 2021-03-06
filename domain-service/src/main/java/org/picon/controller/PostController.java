package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.picon.domain.Member;
import org.picon.domain.Post;
import org.picon.dto.PostDto;
import org.picon.repository.MemberRepository;
import org.picon.repository.PostRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
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
    public List<PostDto> readPostsWithFollowingMembersByLoginId(@RequestParam("identity") String identity) {
        Member loginMember = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        List<Member> followingMembers = loginMember.getFollowingMembers();
        followingMembers.add(loginMember);
        List<Post> findPosts = postRepository.findAllByMemberIn(followingMembers);
        List<PostDto> collect = findPosts.stream()
                .map(e -> modelMapper.map(e, PostDto.class))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping(path = "/member")
    public List<PostDto> readPostsById(@RequestParam("id") Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        List<Post> findPosts = postRepository.findAllByMember(member);
        List<PostDto> collect = findPosts.stream()
                .map(e -> modelMapper.map(e, PostDto.class))
                .collect(Collectors.toList());
        return collect;
    }

    @PostMapping(path = "/")
    @Transactional
    public PostDto createPost(@RequestBody PostDto postDto, @RequestParam("identity") String identity) {
        Member member = memberRepository.findByIdentity(identity)
                .orElseThrow(EntityNotFoundException::new);
        Post post = modelMapper.map(postDto, Post.class);
        post.setMember(member);
        Post save = postRepository.save(post);

        return modelMapper.map(save, PostDto.class);
    }

    @DeleteMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id, @RequestParam("identity") String identity) {
        postRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

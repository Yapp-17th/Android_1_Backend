package org.picon.service;


import org.picon.dto.PostDto;
import org.springframework.stereotype.Component;

@Component
public class FeignPostRemoteServiceFallbackImpl implements FeignPostRemoteService {
    @Override public PostDto readPost(Long postId) {
        return null;
    }

    @Override public PostDto createPost(PostDto post) {
        return null;
    }
}

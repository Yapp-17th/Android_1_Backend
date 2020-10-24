package org.picon.service;

import org.picon.dto.Post;
import org.springframework.stereotype.Component;

@Component
public class FeignPostRemoteServiceFallbackImpl implements FeignPostRemoteService {
    @Override public Post getPostInfo(Long postId) {
        return null;
    }

    @Override public Post createPost(Post post) {
        return null;
    }
}

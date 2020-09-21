package org.picon.service;

import org.springframework.stereotype.Component;

@Component
public class FeignPostRemoteServiceFallbackImpl implements FeignPostRemoteService {
    @Override
    public String getPostInfo(Long postId) {
        return "this post is not available ??? ";
    }
}

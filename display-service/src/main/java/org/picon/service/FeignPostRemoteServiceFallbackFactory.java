package org.picon.service;

import feign.hystrix.FallbackFactory;
import org.picon.dto.post.PostDto;
import org.springframework.stereotype.Component;

@Component
public class FeignPostRemoteServiceFallbackFactory implements FallbackFactory<FeignPostRemoteService> {
    @Override
    public FeignPostRemoteService create(Throwable cause) {
        System.out.println("t = " + cause);
        return new FeignPostRemoteService() {
            @Override public PostDto readPost(Long postId) {
                return null;
            }

            @Override public PostDto createPost(PostDto post) {
                return null;
            }
        };
    }
}

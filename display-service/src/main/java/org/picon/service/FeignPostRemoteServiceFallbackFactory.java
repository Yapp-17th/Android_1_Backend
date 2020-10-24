package org.picon.service;

import feign.hystrix.FallbackFactory;
import org.picon.dto.Post;
import org.springframework.stereotype.Component;

@Component
public class FeignPostRemoteServiceFallbackFactory implements FallbackFactory<FeignPostRemoteService> {
    @Override
    public FeignPostRemoteService create(Throwable cause) {
        System.out.println("t = " + cause);
        return new FeignPostRemoteService() {
            @Override public Post readPost(Long postId) {
                return null;
            }

            @Override public Post createPost(Post post) {
                return null;
            }
        };
    }
}

package org.picon.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignPostRemoteServiceFallbackFactory implements FallbackFactory<FeignPostRemoteService> {
    @Override
    public FeignPostRemoteService create(Throwable cause) {
        System.out.println("t = " + cause);
        return postId -> "this post is not available";
    }
}

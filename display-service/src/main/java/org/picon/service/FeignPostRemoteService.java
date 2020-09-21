package org.picon.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "domain", fallbackFactory = FeignPostRemoteServiceFallbackFactory.class)
public interface FeignPostRemoteService {
    @RequestMapping(path = "/posts/{postId}")
    String getPostInfo(@PathVariable("postId") Long postId);
}

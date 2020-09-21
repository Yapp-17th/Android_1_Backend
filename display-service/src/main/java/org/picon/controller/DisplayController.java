package org.picon.controller;

import lombok.RequiredArgsConstructor;
import org.picon.service.FeignPostRemoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/displays")
@RequiredArgsConstructor
public class DisplayController {
    private final FeignPostRemoteService feignPostRemoteService;

    @GetMapping(path = "/posts/{postId}")
    public String getDisplayDetail(@PathVariable Long postId) {
        String postInfo = getPostInfo(postId);
        return String.format("display id : post, info : " + postInfo);
    }

    private String getPostInfo(Long postId) {
        return feignPostRemoteService.getPostInfo(postId);
    }

}

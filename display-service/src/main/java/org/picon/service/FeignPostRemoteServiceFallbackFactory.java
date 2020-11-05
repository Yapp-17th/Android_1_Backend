package org.picon.service;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.picon.dto.post.PostDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@Slf4j
public class FeignPostRemoteServiceFallbackFactory implements FallbackFactory<FeignPostRemoteService> {
    @Override
    public FeignPostRemoteService create(Throwable cause) {
        log.info("============Start FeignPostRemoteServiceFallbackFactory's Throwable===================");
        log.info(String.valueOf(cause));
        log.info("============End FeignPostRemoteServiceFallbackFactory's Throwable===================");
        return new FeignPostRemoteService() {
            @Override public PostDto readPost(Long postId) {
                return null;
            }

            @Override public PostDto createPost(PostDto post) {
                return null;
            }

            @Override
            public List<String> ImagesUpload(MultipartFile[] multipartFiles) {
                return null;
            }

        };
    }
}

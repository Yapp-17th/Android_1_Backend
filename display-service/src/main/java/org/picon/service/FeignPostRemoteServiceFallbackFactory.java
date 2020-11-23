package org.picon.service;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.picon.dto.BaseResponse;
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
        log.info(String.valueOf(cause.getStackTrace()));
        log.info("============End FeignPostRemoteServiceFallbackFactory's Throwable===================");
        return new FeignPostRemoteServiceFallback(cause);
    }
}

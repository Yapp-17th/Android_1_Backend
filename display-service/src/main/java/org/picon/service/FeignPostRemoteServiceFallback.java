package org.picon.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.picon.dto.post.PostDto;
import org.picon.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FeignPostRemoteServiceFallback implements FeignPostRemoteService {
    private final Throwable cause;

    @Override public List<PostDto> readPostsByMember(String email) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("404 error took place"
                    + ". Error message: "
                    + cause.getLocalizedMessage());
            throw new RuntimeException(cause);
        } else {
            log.error("Other error took place: " + cause.getLocalizedMessage());
            throw new BusinessException(cause);
        }
    }

    @Override public PostDto createPost(PostDto post, String email) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("404 error took place"
                    + ". Error message: "
                    + cause.getLocalizedMessage());
            throw new RuntimeException(cause);
        } else {
            log.error("Other error took place: " + cause.getLocalizedMessage());
            throw new BusinessException(cause);
        }
    }

    @Override
    public List<String> ImagesUpload(MultipartFile[] multipartFiles) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("404 error took place"
                    + ". Error message: "
                    + cause.getLocalizedMessage());
            throw new RuntimeException(cause);
        } else {
            log.error("Other error took place: " + cause.getLocalizedMessage());
            throw new BusinessException(cause);
        }
    }

    @Override
    public ResponseEntity deletePost(Long id, String email) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("404 error took place"
                    + ". Error message: "
                    + cause.getLocalizedMessage());
            throw new RuntimeException(cause);
        } else {
            log.error("Other error took place: " + cause.getLocalizedMessage());
            throw new BusinessException(cause);
        }
    }
}

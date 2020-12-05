package org.picon.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.picon.dto.member.MemberDto;
import org.picon.dto.member.ProfileRequest;
import org.picon.dto.member.ProfileResponse;
import org.picon.dto.post.PostDto;
import org.picon.dto.statics.StatisticsDto;
import org.picon.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FeignPostRemoteServiceFallback implements FeignPostRemoteService {
    private final Throwable cause;

    @Override public List<PostDto> readPostsByMember(String identity) {
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

    @Override public PostDto createPost(PostDto post, String identity) {
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
    public ResponseEntity deletePost(Long id, String identity) {
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
    public StatisticsDto getPostsByStatistics(int year, int month, String identity) {
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

    @Override public MemberDto getMember(String identity) {
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
    public ProfileResponse ImageUpload(MultipartFile multipartFile) {

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
    public MemberDto UploadProfile(String identity, ProfileRequest profileRequest) {

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

    @Override public List<MemberDto> searchMember(String identity, String input) {
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

    @Override public void follow(String identity, Long followMemberId) {
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
    public void deleteProfile(String identityByToken) {
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

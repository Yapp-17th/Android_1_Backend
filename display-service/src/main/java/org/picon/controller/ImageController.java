package org.picon.controller;

import lombok.RequiredArgsConstructor;
import org.picon.dto.statics.StatisticsDto;
import org.picon.exception.NotFoundException;
import org.picon.service.FeignPostRemoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/display/post")
@RequiredArgsConstructor
public class ImageController {
    private final FeignPostRemoteService feignPostRemoteService;

    @PostMapping("/images")
    public ResponseEntity<?> ImagesUpload(@RequestPart("images") MultipartFile[] multipartFiles) {
        List<String> strings = feignPostRemoteService.ImagesUpload(multipartFiles);
        return ResponseEntity.ok().body(strings);
    }

    public void validateResponse(String images) {
        if (images == null) {
            throw new NotFoundException("이미지 저장에 실패하였습니다");
        }
    }
}

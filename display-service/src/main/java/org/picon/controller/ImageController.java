package org.picon.controller;

import lombok.RequiredArgsConstructor;
import org.picon.service.FeignPostRemoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/display")
@RequiredArgsConstructor
public class ImageController {
    private final FeignPostRemoteService feignPostRemoteService;

    @PostMapping("/post/images")
    public ResponseEntity<?> ImagesUpload(@RequestPart("images") MultipartFile[] multipartFiles) {
        List<String> strings = feignPostRemoteService.ImagesUpload(multipartFiles);
        return ResponseEntity.ok().body(strings);
    }

    @PostMapping("/member/image")
    public ResponseEntity<?> ImageUpload(@RequestPart("image") MultipartFile multipartFile) {
        return ResponseEntity.ok().body(feignPostRemoteService.ImageUpload(multipartFile));
    }
}

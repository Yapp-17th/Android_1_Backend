package org.picon.controller;

import lombok.RequiredArgsConstructor;
import org.picon.s3.S3Uploader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class ImageController {

    private final S3Uploader s3Uploader;

    @PostMapping("/images")
    public List<String> ImagesUpload(@RequestParam("images") MultipartFile[] multipartFiles) throws IOException {
        return s3Uploader.uploadImages(multipartFiles, "picon");
    }

}

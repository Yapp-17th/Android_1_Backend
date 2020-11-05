package org.picon.controller;

import feign.form.spring.SpringFormEncoder;
import lombok.RequiredArgsConstructor;
import org.picon.service.FeignPostRemoteService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/displays/post")
@RequiredArgsConstructor
public class ImageController {
    private final FeignPostRemoteService feignPostRemoteService;

    @PostMapping("/images")
    public List<String> ImagesUpload(@RequestPart("images") MultipartFile[] multipartFiles) throws IOException {
        return feignPostRemoteService.ImagesUpload(multipartFiles);
    }
}

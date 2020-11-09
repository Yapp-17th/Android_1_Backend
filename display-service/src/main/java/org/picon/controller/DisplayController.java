package org.picon.controller;

import lombok.RequiredArgsConstructor;
import org.picon.service.FeignPostRemoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/display")
@RequiredArgsConstructor
public class DisplayController {
    private final FeignPostRemoteService feignPostRemoteService;

    @GetMapping(path = "/")
    public String test() {
        return "hello";
    }

}

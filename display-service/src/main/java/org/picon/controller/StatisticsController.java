package org.picon.controller;

import lombok.RequiredArgsConstructor;
import org.picon.dto.statics.StatisticsDto;
import org.picon.jwt.JwtService;
import org.picon.service.FeignPostRemoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "/display")
@RequiredArgsConstructor
public class StatisticsController {

    private final FeignPostRemoteService feignPostRemoteService;
    private final JwtService jwtService;

    @GetMapping("/statistics/{month}")
    public ResponseEntity getPostsByStatistics(@RequestHeader("AccessToken") String accessToken, @PathVariable("month") int month) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        StatisticsDto statisticsDto = feignPostRemoteService.getPostsByStatistics(month,identityByToken);
        return ResponseEntity.ok().body(statisticsDto);
    }

}

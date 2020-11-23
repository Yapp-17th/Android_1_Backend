package org.picon.controller;

import lombok.RequiredArgsConstructor;
import org.picon.dto.statics.StatisticsDto;
import org.picon.exception.NotFoundException;
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

    @GetMapping("/statistics/{year}/{month}")
    public ResponseEntity getPostsByStatistics(@RequestHeader("AccessToken") String accessToken, @PathVariable("year") int year, @PathVariable("month") int month) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        StatisticsDto statisticsDto = feignPostRemoteService.getPostsByStatistics(year, month, identityByToken);
        return ResponseEntity.ok().body(statisticsDto);
    }

    public void validateResponse(StatisticsDto statisticsDto) {
        if (statisticsDto == null) {
            throw new NotFoundException("해당 날에 게시글이 존재하지 않습니다.");
        }
    }
}

package org.picon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.picon.dto.BaseResponse;
import org.picon.dto.member.MemberDto;
import org.picon.dto.member.MemberResponse;
import org.picon.dto.member.MemberSearchResponse;
import org.picon.dto.member.ProfileRequest;
import org.picon.dto.post.PostDto;
import org.picon.dto.post.PostResponse;
import org.picon.jwt.JwtService;
import org.picon.service.FeignPostRemoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/display")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final FeignPostRemoteService feignPostRemoteService;
    private final JwtService jwtService;

    @PostMapping("/member/profile")
    public ResponseEntity<?> uploadProfile(@RequestHeader("AccessToken")String accessToken,@RequestBody ProfileRequest profileRequest) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        MemberDto memberDto = feignPostRemoteService.uploadProfile(identityByToken,profileRequest);
        return ResponseEntity.ok().body(new MemberResponse(memberDto));
    }

    @DeleteMapping("/member/profile")
    public ResponseEntity<?> deleteProfile(@RequestHeader("AccessToken")String accessToken) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        feignPostRemoteService.deleteProfile(identityByToken);
        return ResponseEntity.ok().body(new BaseResponse());
    }

    @GetMapping("/member/")
    public ResponseEntity<?> getMember(@RequestHeader("AccessToken") String accessToken) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        MemberDto memberDto = feignPostRemoteService.getMember(identityByToken);
        return ResponseEntity.ok().body(new MemberResponse(memberDto));
    }

    @GetMapping("/member/search")
    public ResponseEntity<?> searchMember(@RequestHeader("AccessToken") String accessToken, @RequestParam("input") String input) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        List<MemberDto> memberDtos = feignPostRemoteService.searchMember(identityByToken, input);
        return ResponseEntity.ok().body(new MemberSearchResponse(memberDtos));
    }

    @PostMapping("/member/follow/{id}")
    public ResponseEntity<?> follow(@RequestHeader("AccessToken") String accessToken,
                                    @PathVariable("id") Long followingMemberId) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        feignPostRemoteService.follow(identityByToken, followingMemberId);
        return ResponseEntity.ok().body(new BaseResponse());
    }

    @GetMapping("/member/following")
    public ResponseEntity<?> getFollowings(@RequestHeader("AccessToken") String accessToken) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        List<MemberDto> memberDtos = feignPostRemoteService.getFollowingMembers(identityByToken);
        return ResponseEntity.ok().body(new MemberSearchResponse(memberDtos));
    }

    @GetMapping("/member/follower")
    public ResponseEntity<?> getFollower(@RequestHeader("AccessToken") String accessToken) {
        String identityByToken = jwtService.findIdentityByToken(accessToken);
        List<MemberDto> memberDtos = feignPostRemoteService.getFollowerMembers(identityByToken);
        return ResponseEntity.ok().body(new MemberSearchResponse(memberDtos));
    }
}

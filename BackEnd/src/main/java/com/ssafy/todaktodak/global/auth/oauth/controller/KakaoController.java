package com.ssafy.todaktodak.global.auth.oauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.todaktodak.global.auth.oauth.dto.KakaoAccessTokenDto;
import com.ssafy.todaktodak.global.auth.oauth.dto.LoginResponseDto;
import com.ssafy.todaktodak.global.auth.oauth.dto.SocialUserResponseDto;
import com.ssafy.todaktodak.global.auth.oauth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity<LoginResponseDto> kakaoCallback(@RequestParam("code") String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.verificationKakao(code,response);
    }



}

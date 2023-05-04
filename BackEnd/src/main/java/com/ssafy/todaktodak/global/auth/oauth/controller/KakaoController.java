package com.ssafy.todaktodak.global.auth.oauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.todaktodak.global.auth.oauth.dto.LoginResponseDto;
import com.ssafy.todaktodak.global.auth.oauth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

//    @GetMapping("/login/oauth2/callback/kakao")
    @GetMapping("/login/kakao")
    public ResponseEntity<LoginResponseDto> kakaoCallback(@RequestParam("code") String code) throws JsonProcessingException {
        return kakaoService.verificationKakao(code);
    }




}

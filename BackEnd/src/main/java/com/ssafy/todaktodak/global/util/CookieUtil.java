package com.ssafy.todaktodak.global.util;

import com.ssafy.todaktodak.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

import static com.ssafy.todaktodak.global.error.ErrorCode.BAD_REQUEST;

@Slf4j
@Service
@RequiredArgsConstructor
public class CookieUtil {

    public <T> ResponseEntity<T> setTokenCookie(String refreshToken, T inputDto) {
        int cookieTime = 7 * 24 * 60 * 60;
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(cookieTime) // 쿠키 유효기간 설정 (초 단위)
                .path("/") // 쿠키의 경로 설정
                .secure(true) // HTTPS에서만 쿠키를 전송하도록 설정
                .sameSite("None") // SameSite 설정
                .httpOnly(true) // JavaScript에서 쿠키에 접근하지 못하도록 설정
                .build();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (response == null) throw new CustomException(BAD_REQUEST);
        response.setHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok(inputDto);
    }

}


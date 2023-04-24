package com.ssafy.todaktodak.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 1000, "잘못된 요청입니다."),

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 1001,"허용되지 않은 메서드입니다."),

    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, -2,"허용되지 않은 메서드입니다."),;


    private final HttpStatus status;
    private final int code;
    private final String message;


}

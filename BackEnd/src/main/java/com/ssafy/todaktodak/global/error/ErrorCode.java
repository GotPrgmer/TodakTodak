package com.ssafy.todaktodak.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 1000, "잘못된 요청입니다."),

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 1001,"허용되지 않은 메서드입니다."),

    //인증
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, 1002,"허용되지 않은 메서드입니다."),

    JWT_TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, 1003,"jwt토큰 인증 실패입니다."),

    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, 1004,"만료된 jwt토큰입니다."),



    // 컨트롤러 단
    BIRTH_DATE_NOT_VALID(HttpStatus.BAD_REQUEST, 2000, "태어난 날을 잘못 설정하였습니다."),

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, 2001, "원하는 결과를 찾을 수 없습니다."),

    JSON_DATA_INVALID(HttpStatus.BAD_REQUEST, 2002, "유효한 데이터가 아닙니다."),

    // 기타 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 2000, "내부 서버 오류입니다.");





    private final HttpStatus status;
    private final int code;
    private final String message;


    }

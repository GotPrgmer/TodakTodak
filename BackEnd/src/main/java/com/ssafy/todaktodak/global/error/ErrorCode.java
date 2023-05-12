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
    JWT_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, 1003,"잘못된 jwt토큰입니다."),

    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, 1004,"만료된 jwt토큰입니다."),

    REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED,1005,"리프레시토큰이 만료되었습니다."),



    // 컨트롤러 단
    BIRTH_DATE_INVALID(HttpStatus.BAD_REQUEST, 2000, "태어난 날을 잘못 설정하였습니다."),

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, 2001, "엔티티를 찾을 수 없습니다."),

    JSON_DATA_INVALID(HttpStatus.BAD_REQUEST, 2002, "반환값이 유효하지 않습니다."),

    // 오픈비두

    SESSION_ID_INVALID(HttpStatus.NOT_FOUND, 3000, "세션이 존재하지 않습니다."),

    // 기타 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "내부 서버 오류입니다.");





    private final HttpStatus status;
    private final int code;
    private final String message;


    }

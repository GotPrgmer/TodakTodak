package com.ssafy.todaktodak.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{


    private final ErrorCode errorCode;
}

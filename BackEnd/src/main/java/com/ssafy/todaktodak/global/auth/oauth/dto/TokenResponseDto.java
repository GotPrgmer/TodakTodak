package com.ssafy.todaktodak.global.auth.oauth.dto;

import lombok.Builder;

@Builder
public class TokenResponseDto {

    private String accessToken;

    public static TokenResponseDto ofAccessToken(String accessToken){
        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }


}

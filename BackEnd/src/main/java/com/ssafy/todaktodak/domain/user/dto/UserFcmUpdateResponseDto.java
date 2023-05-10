package com.ssafy.todaktodak.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserFcmUpdateResponseDto {

    private String fcmKey;
    public static UserFcmUpdateResponseDto ofFcm(String fcmKey) {
        return UserFcmUpdateResponseDto.builder()
                .fcmKey(fcmKey)
                .build();
    }


}

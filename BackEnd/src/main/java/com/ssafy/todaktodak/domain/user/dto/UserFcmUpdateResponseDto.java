package com.ssafy.todaktodak.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.todaktodak.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

package com.ssafy.todaktodak.domain.device.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeviceAlarmResponseDto {


    private String fcmKey;
    private String message;


    public static DeviceAlarmResponseDto of(String message,String fcmKey){
        return DeviceAlarmResponseDto.builder()
                .fcmKey(fcmKey)
                .message(message)
                .build();
    }
}

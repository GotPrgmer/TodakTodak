package com.ssafy.todaktodak.domain.device.dto;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.domain.Gender;
import com.ssafy.todaktodak.domain.baby.dto.BabyInfoResponseDto;
import com.ssafy.todaktodak.domain.device.domain.Device;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeviceInfoResponseDto {
    private String serialNumber;

    private String sessionId;

    private String sessionToken;


    public static DeviceInfoResponseDto ofDevice(Device device) {

        return DeviceInfoResponseDto.builder()
                .serialNumber(device.getSerialNumber())
                .sessionToken(device.getSessionToken())
                .sessionId(device.getSessionId())
                .build();

    }



}

package com.ssafy.todaktodak.global.openvidu.dto;

import com.ssafy.todaktodak.domain.device.domain.Device;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenViduCreateSessionResponseDto {

    private String sessionsId;

    public static OpenViduCreateSessionResponseDto ofDevice(Device device){

        return OpenViduCreateSessionResponseDto.builder()
                .sessionsId(device.getSessionId())
                .build();

    }


}

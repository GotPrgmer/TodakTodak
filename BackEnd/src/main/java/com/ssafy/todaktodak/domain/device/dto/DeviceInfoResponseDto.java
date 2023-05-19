package com.ssafy.todaktodak.domain.device.dto;

import com.ssafy.todaktodak.domain.device.domain.Device;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeviceInfoResponseDto {
    private String serialNumber;

    private String sessionId;

    private String connectionId;


    public static DeviceInfoResponseDto ofDevice(Device device) {

        return DeviceInfoResponseDto.builder()
                .serialNumber(device.getDeviceSerialNumber())
                .connectionId(device.getDeviceConnectionId())
                .sessionId(device.getDeviceSessionId())
                .build();

    }



}

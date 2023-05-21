package com.ssafy.todaktodak.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DeviceAlarmRequestDto {

    @JsonProperty("serialNumber")
    private String serialNumber;

    @JsonProperty("alarmType")
    private String alarmType;


    @JsonProperty("message")
    private String message;

}

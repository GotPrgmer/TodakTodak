package com.ssafy.todaktodak.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DeviceInfoUpdateRequestDto {

    @JsonProperty("sessionId")
    private String sessionId;
}

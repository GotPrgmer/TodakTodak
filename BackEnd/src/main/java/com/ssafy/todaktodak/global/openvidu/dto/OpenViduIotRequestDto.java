package com.ssafy.todaktodak.global.openvidu.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class OpenViduIotRequestDto {
    @JsonProperty("paramsSessions")
    private Map<String, Object> paramsSessions;

    @JsonProperty("paramsConnections")
    private Map<String, Object> paramsConnections;

}
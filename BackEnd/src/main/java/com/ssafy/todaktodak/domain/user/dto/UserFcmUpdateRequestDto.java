package com.ssafy.todaktodak.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserFcmUpdateRequestDto {

    @JsonProperty("fcmKey")
    private String fcmKey;
}

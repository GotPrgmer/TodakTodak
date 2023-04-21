package com.ssafy.todaktodak.global.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoOauthTokenResponseProperties {
    @JsonProperty("profile_image")
    private String profileImage;
}
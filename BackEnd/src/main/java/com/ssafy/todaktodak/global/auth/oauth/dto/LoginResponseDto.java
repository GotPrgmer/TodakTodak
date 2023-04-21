package com.ssafy.todaktodak.global.auth.oauth.dto;

import com.ssafy.todaktodak.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private Integer id;

    private String nickname;

    private String email;


    private String userImageUrl;

    private String jwtToken;

    public static LoginResponseDto toEntity(User user, String jwtToken) {
        return LoginResponseDto.builder()
                .id(user.getUserId())
                .email(user.getUserEmail())
                .nickname(user.getUserNickname())
                .userImageUrl(user.getUserImageUrl())
                .jwtToken(jwtToken)
                .build();
    }
}

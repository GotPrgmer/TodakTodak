package com.ssafy.todaktodak.global.auth.oauth.dto;

import com.ssafy.todaktodak.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoginResponseDto {
    private Integer id;

    private List<Integer> babyIds;

    private String nickname;

    private String email;


    private String userImageUrl;

    private String jwtToken;

    public static LoginResponseDto ofLoginInfo(User user, List<Integer> babyIds, String jwtToken) {
        return LoginResponseDto.builder()
                .id(user.getUserId())
                .babyIds(babyIds)
                .email(user.getUserEmail())
                .nickname(user.getUserNickname())
                .userImageUrl(user.getUserImageUrl())
                .jwtToken(jwtToken)
                .build();
    }

}

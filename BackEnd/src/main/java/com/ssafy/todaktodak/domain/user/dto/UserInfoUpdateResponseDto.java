package com.ssafy.todaktodak.domain.user.dto;

import com.ssafy.todaktodak.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class UserInfoUpdateResponseDto {

    private String userEmail;

    private String userImageUrl;

    private String userNickname;

    private String createdDate;

    public static UserInfoUpdateResponseDto of(User user) {
        LocalDateTime signUpDateTime = user.getUserCreatedDate();
        String localDateTimeFormat2
                = signUpDateTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );

        return UserInfoUpdateResponseDto.builder()
                .userEmail(user.getUserEmail())
                .userImageUrl(user.getUserImageUrl())
                .userNickname(user.getUserNickname())
                .createdDate(localDateTimeFormat2)
                .build();
    }

}
package com.ssafy.todaktodak.global.firebase.dto;

import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
@Getter
public class FcmMessageDto {

    private boolean validate_only;
    private Message message;


    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification; // 모든 mobile os를 아우를수 있는 Notification

        private WebpushConfig webpushConfig;
        private String token; // 특정 device에 알림을 보내기위해 사용
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}

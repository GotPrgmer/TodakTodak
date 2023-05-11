package com.ssafy.todaktodak.global.firebase.service;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.ssafy.todaktodak.global.firebase.dto.FcmMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseService {

    @Value("${fcm.key.path}")
    private String FcmKeyPath;

    @Value("${fcm.key.scope}")
    private String GoogleScope;

    @Value("${fcm.key.project-id}")
    private String FcmProjectId;

    private final ObjectMapper objectMapper;

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = FcmKeyPath;
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of(GoogleScope));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }



    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String API_URL = "https://fcm.googleapis.com/v1/projects/"+ FcmProjectId +"/messages:send";
        String message = makeMessage(targetToken, title, body);
        log.info(API_URL);

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON,message);
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        log.info(response.body().string());
    }

    // 파라미터를 FCM이 요구하는 body 형태로 만들어준다.
    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessageDto fcmMessage = FcmMessageDto.builder()
                .message(FcmMessageDto.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessageDto.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

}

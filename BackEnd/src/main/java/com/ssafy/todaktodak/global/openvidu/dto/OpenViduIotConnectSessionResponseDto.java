package com.ssafy.todaktodak.global.openvidu.dto;

import io.openvidu.java.client.Connection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenViduIotConnectSessionResponseDto {

    private String connectionToken;

    public static OpenViduIotConnectSessionResponseDto ofConnection(Connection connection) {

        return OpenViduIotConnectSessionResponseDto.builder()
                .connectionToken(connection.getToken())
                .build();

    }


}

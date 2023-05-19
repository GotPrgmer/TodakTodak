package com.ssafy.todaktodak.global.openvidu.dto;

import io.openvidu.java.client.Connection;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OpenViduCreateConnectionResponseDto {

    private String connectionToken;

    public static OpenViduCreateConnectionResponseDto ofConnection(Connection connection) {
        return OpenViduCreateConnectionResponseDto.builder()
                .connectionToken(connection.getToken())
                .build();
    }
}

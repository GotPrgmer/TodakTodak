package com.ssafy.todaktodak.global.openvidu.service;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.repository.BabyRepository;
import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.device.repository.DeviceRepository;
import com.ssafy.todaktodak.global.error.CustomException;
import com.ssafy.todaktodak.global.error.ErrorCode;
import com.ssafy.todaktodak.global.openvidu.dto.OpenViduCreateConnectionResponseDto;
import com.ssafy.todaktodak.global.openvidu.dto.OpenViduIotConnectSessionResponseDto;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class OpenViduService {


    private final BabyRepository babyRepository;

    private final DeviceRepository deviceRepository;


    @Transactional
    public OpenViduIotConnectSessionResponseDto iotOpenViduConnection(Map<String, Object> paramsSessions, Map<String, Object> paramsConnections, OpenVidu openVidu) throws OpenViduJavaClientException, OpenViduHttpException {
        Session currentSession = null;
        currentSession = openVidu.getActiveSession(paramsSessions.get("customSessionId").toString());// OpenVidu 클래스의 인스턴스의 getActiveSession 메소드를 호출한다.
        if (currentSession != null) {
            List<Connection> connections = currentSession.getActiveConnections();
            if (!connections.isEmpty()) {
                for (Connection connection : currentSession.getActiveConnections()) {
                    if (connection != null && connection.getStatus().equals("connected")) {
                        currentSession.forceDisconnect(connection);
                    }
                }
            }
        }

        SessionProperties sessionsProperties = SessionProperties.fromJson(paramsSessions).build();
        Session session = openVidu.createSession(sessionsProperties);
        ConnectionProperties connectionProperties = ConnectionProperties.fromJson(paramsConnections).build();
        Connection connection = session.createConnection(connectionProperties);
        return OpenViduIotConnectSessionResponseDto.ofConnection(connection);
    }


    @Transactional
    public OpenViduCreateConnectionResponseDto createConnection(Integer babyId, Map<String, Object> params, OpenVidu openVidu, String sessionId, String userId) throws OpenViduJavaClientException, OpenViduHttpException {
        // 아기 id와 세션id는 일치한다고 가정
        Integer userIdToInteger = Integer.parseInt(userId);

        Device getDevice = findDeviceWithUserAndBaby(babyId, userIdToInteger);
        Session session = openVidu.getActiveSession(sessionId);// OpenVidu 클래스의 인스턴스의 getActiveSession 메소드를 호출한다.
        if (session == null) {    // 세션 ID가 존재하지 않는다면
            throw new CustomException(ErrorCode.SESSION_ID_INVALID); // 404 에러를 반환한다.
        }


        if (getDevice.getDeviceConnectionId() == null) {
            //커넥션ID가 없을 경우
            ConnectionProperties properties = ConnectionProperties.fromJson(params).build(); // ConnectionProperties 클래스의 인스턴스를 생성한다.
            Connection connection = session.createConnection(properties); // Session 클래스의 인스턴스의 createConnection 메소드를 호출한다.
            getDevice.updateConnectionId(connection);
        } else {
            Connection currentConnection = session.getConnection(getDevice.getDeviceConnectionId());
            if (currentConnection == null || !currentConnection.getStatus().equals("CONNECTED")) {
                ConnectionProperties properties = ConnectionProperties.fromJson(params).build(); // ConnectionProperties 클래스의 인스턴스를 생성한다.
                Connection connection = session.createConnection(properties); // Session 클래스의 인스턴스의 createConnection 메소드를 호출한다.
                getDevice.updateConnectionId(connection);
            }
        }

        return OpenViduCreateConnectionResponseDto.ofConnection(session.getConnection(getDevice.getDeviceConnectionId()));
    }

    public Device findDeviceWithUserAndBaby(Integer babyId, Integer userId) {
        Optional<Baby> findBaby = babyRepository.findByBabyIdAndUserUserId(babyId, userId);
        if (findBaby.isEmpty()) {
            throw new CustomException(ErrorCode.BABY_NOT_FOUND);
        }
        Baby getBaby = findBaby.get();
        //device에 넣기
        Optional<Device> findDevice = deviceRepository.findByBabyBabyId(getBaby.getBabyId());
        if (findDevice.isEmpty()) {
            throw new CustomException(ErrorCode.DEVICE_NOT_FOUND);
        }

        return findDevice.get();
    }
}

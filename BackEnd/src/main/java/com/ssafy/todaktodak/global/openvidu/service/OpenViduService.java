package com.ssafy.todaktodak.global.openvidu.service;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.repository.BabyRepository;
import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.device.repository.DeviceRepository;
import com.ssafy.todaktodak.global.error.CustomException;
import com.ssafy.todaktodak.global.error.ErrorCode;
import com.ssafy.todaktodak.global.openvidu.dto.OpenViduCreateConnectionResponseDto;
import com.ssafy.todaktodak.global.openvidu.dto.OpenViduCreateSessionResponseDto;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenViduService {



    private final BabyRepository babyRepository;

    private final DeviceRepository deviceRepository;


    @Transactional
    public OpenViduCreateSessionResponseDto createSession(Integer babyId, Map<String, Object> params,OpenVidu openVidu) throws OpenViduJavaClientException, OpenViduHttpException {
//        Optional<Baby> findBaby = babyRepository.findByBabyIdAndUserUserId(babyId);

        Device getDevice = findDeviceWithUserAndBaby(babyId);
        log.info(getDevice.getSessionId());

        if(getDevice.getSessionId()==null){
            String customSessionId = "todaktodak" + (1000 + babyId);
            params.put("customSessionId", customSessionId);
            SessionProperties properties = SessionProperties.fromJson(params).build(); // SessionProperties 클래스의 인스턴스를 생성한다.
            Session session = openVidu.createSession(properties);	// OpenVidu 클래스의 인스턴스의 createSession 메소드를 호출한다.
            getDevice.updateSessionId(session);
            log.info(getDevice.getSessionId());
        }
        else{
            String customSessionId = "todaktodak" + (1000 + babyId);
            params.put("customSessionId", customSessionId);
            SessionProperties properties = SessionProperties.fromJson(params).build(); // SessionProperties 클래스의 인스턴스를 생성한다.
            Session session = openVidu.createSession(properties);	// OpenVidu 클래스의 인스턴스의 createSession 메소드를 호출한다.

            if(!getDevice.getSessionId().equals(session.getSessionId())){
                getDevice.updateSessionId(session);
            }
        }
        return OpenViduCreateSessionResponseDto.ofDevice(getDevice);
    }

    @Transactional
    public OpenViduCreateConnectionResponseDto createConnect(Integer babyId,Map<String, Object> params, OpenVidu openVidu,String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {
        // 아기 id와 세션id는 일치한다고 가정

        Device getDevice = findDeviceWithUserAndBaby(babyId);
        Session session = openVidu.getActiveSession(sessionId);// OpenVidu 클래스의 인스턴스의 getActiveSession 메소드를 호출한다.
		if (session == null) {    // 세션 ID가 존재하지 않는다면
            throw new CustomException(ErrorCode.SESSION_ID_NOT_VALID); // 404 에러를 반환한다.
        }
        log.info(session.getSessionId());


        if (getDevice.getConnectionId() == null) {
            //커넥션ID가 없을 경우
            ConnectionProperties properties = ConnectionProperties.fromJson(params).build(); // ConnectionProperties 클래스의 인스턴스를 생성한다.
            Connection connection = session.createConnection(properties); // Session 클래스의 인스턴스의 createConnection 메소드를 호출한다.
            getDevice.updateCreateId(connection);
        }
        else {
            Connection currentConnection = session.getConnection(getDevice.getConnectionId());
            if (currentConnection == null || !currentConnection.getStatus().equals("CONNECTED")) {
                ConnectionProperties properties = ConnectionProperties.fromJson(params).build(); // ConnectionProperties 클래스의 인스턴스를 생성한다.
                Connection connection = session.createConnection(properties); // Session 클래스의 인스턴스의 createConnection 메소드를 호출한다.
                getDevice.updateCreateId(connection);
            }
        }

        return OpenViduCreateConnectionResponseDto.ofConnection(session.getConnection(getDevice.getConnectionId()));
    }

    public Device findDeviceWithUserAndBaby(Integer babyId){
        Optional<Baby> findBaby = babyRepository.findById(babyId);
        if (findBaby.isEmpty()){
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        Baby getBaby = findBaby.get();
        //device에 넣기
        Optional<Device> findDevice = deviceRepository.findByBabyBabyId(getBaby.getBabyId());
        if (findDevice.isEmpty()){
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }

        Device getDevice = findDevice.get();
        return getDevice;
    }
}



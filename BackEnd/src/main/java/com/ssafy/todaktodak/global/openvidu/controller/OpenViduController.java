package com.ssafy.todaktodak.global.openvidu.controller;

import com.ssafy.todaktodak.global.openvidu.dto.OpenViduCreateConnectionResponseDto;
import com.ssafy.todaktodak.global.openvidu.dto.OpenViduIotConnectSessionResponseDto;
import com.ssafy.todaktodak.global.openvidu.dto.OpenViduIotRequestDto;
import com.ssafy.todaktodak.global.openvidu.service.OpenViduService;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

/*
 * 해당 컨트롤러의 역할은 OpenVidu 서버와 통신하여 OpenVidu 서버에 세션을 생성하고, 세션에 연결을 생성하는 것이다.
 *
 * 세션을 연결하는 과정은 다음과 같다.
 * 1. 클라이언트가 OpenVidu 서버에 세션을 생성한다.
 * 2. 클라이언트가 OpenVidu 서버에 연결을 생성한다.
 * 3. 클라이언트가 OpenVidu 서버에 연결을 생성할 때, 세션 ID를 전달한다.
 * 4. OpenVidu 서버는 해당 세션 ID를 가진 세션을 찾아 연결을 생성한다.
 *
 */
@RestController
@RequiredArgsConstructor
public class OpenViduController {

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;
    private OpenVidu openvidu;


    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET); // OpenVidu 클래스의 인스턴스를 생성한다.
    }

    /**
     * @param params The Session properties
     * @return The Session ID
     * <p>
     * This method creates a new Session in OpenVidu Server. The session
     */
    private final OpenViduService openViduService;

    @PostMapping(value = "/api/iot/sessions", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public OpenViduIotConnectSessionResponseDto iotOpenViduConnection(@RequestBody OpenViduIotRequestDto params)
            throws OpenViduJavaClientException, OpenViduHttpException {

        return openViduService.iotOpenViduConnection(params.getParamsSessions(), params.getParamsConnections(), this.openvidu);
    }


    /**
     * @param sessionId The Session in which to create the Connection
     * @param params    The Connection properties
     * @return The Token associated to the Connection
     * <p>
     * This method creates a new Connection in OpenVidu Server. The connection
     */
    @PatchMapping(value = "/api/sessions/{sessionId}/connections/{babyId}", consumes = {"application/json;charset=UTF-8"})
    public OpenViduCreateConnectionResponseDto createConnection(Authentication authentication, @PathVariable("sessionId") String sessionId,
                                                                @PathVariable("babyId") Integer babyId,
                                                                @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return openViduService.createConnection(babyId, params, this.openvidu, sessionId, principal.getUsername());
    }
}

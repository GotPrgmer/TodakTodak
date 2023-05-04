package com.ssafy.todaktodak.domain.device.controller;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;

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
@CrossOrigin(origins = "http://localhost:3000", maxAge=3600)
public class DeviceController {

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
	 * 
	 * 	   This method creates a new Session in OpenVidu Server. The session
	 */
	@PostMapping(value = "/api/sessions", consumes = {"application/json;charset=UTF-8", "text/plain;charset=UTF-8"})
	public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
			throws OpenViduJavaClientException, OpenViduHttpException {
		SessionProperties properties = SessionProperties.fromJson(params).build(); // SessionProperties 클래스의 인스턴스를 생성한다.
		Session session = openvidu.createSession(properties);	// OpenVidu 클래스의 인스턴스의 createSession 메소드를 호출한다.

		return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK); // 세션 ID를 반환한다.
	}

	/**
	 * @param sessionId The Session in which to create the Connection
	 * @param params    The Connection properties
	 * @return The Token associated to the Connection
	 * 
	 * 	   This method creates a new Connection in OpenVidu Server. The connection
	 */
	@PostMapping("/api/sessions/{sessionId}/connections") 
	public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
			@RequestBody(required = false) Map<String, Object> params)
			throws OpenViduJavaClientException, OpenViduHttpException { 
		Session session = openvidu.getActiveSession(sessionId); 	// OpenVidu 클래스의 인스턴스의 getActiveSession 메소드를 호출한다.
		if (session == null) { 	// 세션 ID가 존재하지 않는다면
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 에러를 반환한다.
		}
		ConnectionProperties properties = ConnectionProperties.fromJson(params).build(); // ConnectionProperties 클래스의 인스턴스를 생성한다.
		Connection connection = session.createConnection(properties); // Session 클래스의 인스턴스의 createConnection 메소드를 호출한다.
		return new ResponseEntity<>(connection.getToken(), HttpStatus.OK); // 토큰을 반환한다.
	}
}

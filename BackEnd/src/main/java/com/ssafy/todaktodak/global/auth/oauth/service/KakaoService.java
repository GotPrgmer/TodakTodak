package com.ssafy.todaktodak.global.auth.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.todaktodak.domain.user.domain.Role;
import com.ssafy.todaktodak.domain.user.domain.User;
import com.ssafy.todaktodak.domain.user.repository.UserRepository;
import com.ssafy.todaktodak.global.auth.jwt.JwtProvider;
import com.ssafy.todaktodak.global.auth.oauth.dto.KakaoAccessTokenDto;
import com.ssafy.todaktodak.global.auth.oauth.dto.LoginResponseDto;
import com.ssafy.todaktodak.global.auth.oauth.dto.SocialUserResponseDto;
import com.ssafy.todaktodak.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${oauth2.client.provider.kakao.token-uri}")
    private String GET_TOKEN_URI;

    @Value("${oauth2.client.provider.kakao.user-info-uri}")
    private String GET_USER_INFO_URI;

    @Value("${oauth2.client.registration.kakao.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${oauth2.client.registration.kakao.client-secret}")
    private String CLIENT_SECRET;


    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final CookieUtil cookieUtil;



    public ResponseEntity<LoginResponseDto> verificationKakao(String code){
        KakaoAccessTokenDto kakaoAccessTokenDto = getAccessTokenByCode(code);
        log.info("빠져나옴");

        SocialUserResponseDto socialUserResponseDto = getUserInfoByAccessToken(kakaoAccessTokenDto.getAccessToken());

        //회원가입이 필요하다면 회원가입
        signUp(socialUserResponseDto);

        //로그인

        return kakaoLogin(socialUserResponseDto.getEmail());

    }


    public KakaoAccessTokenDto getAccessTokenByCode(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI + "kakao");
        params.add("code", code);
        params.add("client_secret", CLIENT_SECRET);
        log.info(CLIENT_ID);
        log.info(params.toString());

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        String url = GET_TOKEN_URI;

        ResponseEntity<String> accessTokenResponse = restTemplate.postForEntity(url, kakaoTokenRequest, String.class);

        log.info(accessTokenResponse.toString());
        KakaoAccessTokenDto kakaoAccessTokenDto = null;

        try {
            kakaoAccessTokenDto = objectMapper.readValue(accessTokenResponse.getBody(), KakaoAccessTokenDto.class);
            log.info(kakaoAccessTokenDto.toString());
        } catch (JsonProcessingException e) {
            //글로벌로 예외처리 하기
            log.error("글로벌로 예외처리 하기");
        }
        return kakaoAccessTokenDto;
    }
    public SocialUserResponseDto getUserInfoByAccessToken(String accessToken) {


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest  = new HttpEntity<>(headers);

        String url = GET_USER_INFO_URI;

        String userInfo = restTemplate.postForObject(url, kakaoUserInfoRequest, String.class);
        log.info(userInfo);

        SocialUserResponseDto socialUserResponseDto = SocialUserResponseDto.builder().build();
        try{
            JsonNode jsonNode = objectMapper.readTree(userInfo);
            String email = String.valueOf(jsonNode.get("kakao_account").get("email")).replaceAll("^\"|\"$", "");
            String nickname = String.valueOf(jsonNode.get("kakao_account").get("profile").get("nickname")).replaceAll("^\"|\"$", "");
            String imageUrl = String.valueOf(jsonNode.get("kakao_account").get("profile").get("profile_image_url")).replaceAll("^\"|\"$", "");
            socialUserResponseDto.socialUserInfo(email, nickname, imageUrl);

        }
        catch (JsonProcessingException e) {
            log.error("글로벌 익셉션 발생!");
        }
        return socialUserResponseDto;
    }

    public void signUp (SocialUserResponseDto socialUserResponseDto) {
        // DB 에 중복된 email이 있는지 확인
        String kakaoEmail = socialUserResponseDto.getEmail();
        User duplicateUser = userRepository.findUserByUserEmail(kakaoEmail)
                .orElse(null);


        if (duplicateUser == null) {
            // 회원가입
            User newMember = User.kakaoSignupMember(socialUserResponseDto);
            userRepository.save(newMember);

        }

    }

    public ResponseEntity<LoginResponseDto> kakaoLogin(String email) {
        Optional<User> user = userRepository.findUserByUserEmail(email);
        User userUnwrapped = null;
        String jwtToken = null;
        String refreshToken = null;
        if (user.isPresent()) {
            userUnwrapped = user.get();
            // User 객체 사용
            jwtToken = jwtProvider.createJwt(userUnwrapped.getUserId().toString(), userUnwrapped.getUserRole()).createAccessToken();
            refreshToken = jwtProvider.createJwt(userUnwrapped.getUserId().toString(), userUnwrapped.getUserRole()).createRefreshToken();

//            String memberId = jwtProvider.getId(jwtToken);
            //        redisUtil.dataExpirationsInput(memberId, refreshToken, 7);
        }
        else{
            log.error("사용자가 없습니다.");
        }
        log.info(refreshToken);

        return cookieUtil.HandlerMethod(refreshToken, LoginResponseDto.toEntity(userUnwrapped, jwtToken));
    }


}

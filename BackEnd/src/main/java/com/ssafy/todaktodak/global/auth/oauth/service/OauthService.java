package com.ssafy.todaktodak.global.auth.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.repository.BabyRepository;
import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.device.repository.DeviceRepository;
import com.ssafy.todaktodak.domain.user.domain.User;
import com.ssafy.todaktodak.domain.user.repository.UserRepository;
import com.ssafy.todaktodak.global.auth.jwt.JwtProvider;
import com.ssafy.todaktodak.global.auth.oauth.dto.KakaoAccessTokenDto;
import com.ssafy.todaktodak.global.auth.oauth.dto.LoginResponseDto;
import com.ssafy.todaktodak.global.auth.oauth.dto.SocialUserResponseDto;
import com.ssafy.todaktodak.global.auth.oauth.dto.TokenResponseDto;
import com.ssafy.todaktodak.global.error.CustomException;
import com.ssafy.todaktodak.global.error.ErrorCode;
import com.ssafy.todaktodak.global.redis.RedisUtil;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthService {

    @Value("${oauth2.client.provider.kakao.token-uri}")
    private String GET_TOKEN_URI;

    @Value("${oauth2.client.provider.kakao.user-info-uri}")
    private String GET_USER_INFO_URI;

    @Value("${oauth2.client.registration.kakao.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${oauth2.client.registration.kakao.redirect-uri1}")
    private String REDIRECT_URI1;

    @Value("${oauth2.client.registration.kakao.redirect-uri2}")
    private String REDIRECT_URI2;

    @Value("${oauth2.client.registration.kakao.client-secret}")
    private String CLIENT_SECRET;

    @Value("${s3-baby-default-image}")
    private String S3_BABY_IMAGE;


    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;

    private final BabyRepository babyRepository;

    private final DeviceRepository deviceRepository;

    private final JwtProvider jwtProvider;

    private final CookieUtil cookieUtil;

    private final RedisUtil redisUtil;



    public ResponseEntity<LoginResponseDto> verification(String code) throws JsonProcessingException {
        KakaoAccessTokenDto kakaoAccessTokenDto = getAccessTokenByCode(code);

        SocialUserResponseDto socialUserResponseDto = getUserInfoByAccessToken(kakaoAccessTokenDto.getAccessToken());

        //회원가입이 필요하다면 회원가입
        signUp(socialUserResponseDto);

        //로그인

        return kakaoLogin(socialUserResponseDto.getEmail());

    }


    public KakaoAccessTokenDto getAccessTokenByCode(String code) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
        params1.add("grant_type", GRANT_TYPE);
        params1.add("client_id", CLIENT_ID);
        params1.add("redirect_uri", REDIRECT_URI1 + "kakao");
        params1.add("code", code);
        params1.add("client_secret", CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest1 = new HttpEntity<>(params1, headers);

        LinkedMultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
        params2.add("grant_type", GRANT_TYPE);
        params2.add("client_id", CLIENT_ID);
        params2.add("redirect_uri", REDIRECT_URI2 + "kakao");
        params2.add("code", code);
        params2.add("client_secret", CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest2 = new HttpEntity<>(params2, headers);

        String url = GET_TOKEN_URI;



        ResponseEntity<String> accessTokenResponse1 = null;
        ResponseEntity<String> accessTokenResponse2 = null;
        try {
            accessTokenResponse1 = restTemplate.postForEntity(url, kakaoTokenRequest1, String.class);
        } catch (Exception e) {
            //글로벌로 예외처리 하기
            log.info("이건아님1");
        }
        try{
            accessTokenResponse2 = restTemplate.postForEntity(url, kakaoTokenRequest2, String.class);

        } catch(Exception e){
            log.info("이건아님");
        }
        KakaoAccessTokenDto kakaoAccessTokenDto = objectMapper.readValue(getNonNullResponse(accessTokenResponse1, accessTokenResponse2).getBody(),KakaoAccessTokenDto.class);

        return kakaoAccessTokenDto;
    }
    public ResponseEntity<String> getNonNullResponse(ResponseEntity<String> response1, ResponseEntity<String> response2) {
        if (response1 != null) {
            return response1;
        } else if (response2 != null) {
            return response2;
        } else {
            return null;
        }
    }
    public SocialUserResponseDto getUserInfoByAccessToken(String accessToken) {


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest  = new HttpEntity<>(headers);

        String url = GET_USER_INFO_URI;

        String userInfo = restTemplate.postForObject(url, kakaoUserInfoRequest, String.class);

        SocialUserResponseDto socialUserResponseDto = SocialUserResponseDto.builder().build();
        try{
            JsonNode jsonNode = objectMapper.readTree(userInfo);
            String email = String.valueOf(jsonNode.get("kakao_account").get("email")).replaceAll("^\"|\"$", "");
            String nickname = String.valueOf(jsonNode.get("kakao_account").get("profile").get("nickname")).replaceAll("^\"|\"$", "");
            String imageUrl = String.valueOf(jsonNode.get("kakao_account").get("profile").get("profile_image_url")).replaceAll("^\"|\"$", "");
            socialUserResponseDto.socialUserInfo(email, nickname, imageUrl);

        }
        catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.JSON_DATA_INVALID);
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
            User newUser = User.kakaoSignupUser(socialUserResponseDto);
            userRepository.save(newUser);
            // 아기 초기 세팅 추가
            Baby newBaby = Baby.newBabyCreate(newUser,S3_BABY_IMAGE);
            babyRepository.save(newBaby);
            // 디바이스 설정
            Device newDevice =  Device.newDeviceCreate(newBaby);
            deviceRepository.save(newDevice);

        }

    }

    public ResponseEntity<LoginResponseDto> kakaoLogin(String email) {

        Optional<User> user = userRepository.findUserByUserEmail(email);

        if ( user.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        User findUser = user.get();
        // User 객체 사용
        String jwtToken = jwtProvider.createJwt(findUser.getUserId().toString(), findUser.getUserRole()).createAccessToken();
        String refreshToken = jwtProvider.createJwt(findUser.getUserId().toString(), findUser.getUserRole()).createRefreshToken();


        List<Baby> babies = babyRepository.findBabiesByUserUserId(findUser.getUserId());
        List<Integer> babyIds = babies.stream()
                .map(Baby::getBabyId)
                .collect(Collectors.toList());



        return cookieUtil.setTokenCookie(refreshToken, LoginResponseDto.ofLoginInfo(findUser,babyIds, jwtToken));
    }

    public ResponseEntity<?> tokenReissue(HttpServletRequest request) {
        //refreshToken얻어오는 방법
        Cookie[] cookies = request.getCookies();

        String refreshTokenCookie = null;
        if (cookies == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
            }
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                refreshTokenCookie = cookie.getValue();
                break;
            }
        }

        if (refreshTokenCookie == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        if (!jwtProvider.validate(refreshTokenCookie)) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        String userId = jwtProvider.getId(refreshTokenCookie);
        String refreshTokenInRedis = redisUtil.getToken(userId);

        if (ObjectUtils.isEmpty(refreshTokenInRedis)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        if (!refreshTokenInRedis.equals(refreshTokenCookie)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_INVALID);
        }
        redisUtil.deleteData(userId);

        Integer userIdNumber = Integer.parseInt(userId);

        Optional<User> user = userRepository.findUserByUserId(userIdNumber);

        if ( user.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        User findUser = user.get();
        //엑세스토큰 재발급
        String newAccessToken = jwtProvider.createJwt(userId, findUser.getUserRole()).createAccessToken();
        //리프레시토큰 재발급
        String newRefreshToken = jwtProvider.createJwt(userId, findUser.getUserRole()).createRefreshToken();

        redisUtil.dataExpirationsInput(userId, newRefreshToken, 7);
        TokenResponseDto tokenRegenerateResponseDto = TokenResponseDto.ofAccessToken(
                newAccessToken);

        return cookieUtil.setTokenCookie(newRefreshToken, tokenRegenerateResponseDto);
    }


}
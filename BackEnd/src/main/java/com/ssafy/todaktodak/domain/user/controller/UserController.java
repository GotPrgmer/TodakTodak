package com.ssafy.todaktodak.domain.user.controller;

import com.ssafy.todaktodak.domain.user.dto.UserInfoResponseDto;
import com.ssafy.todaktodak.domain.user.dto.UserInfoUpdateRequestDto;
import com.ssafy.todaktodak.domain.user.dto.UserInfoUpdateResponseDto;
import com.ssafy.todaktodak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Service
public class UserController {

    private final UserService userService;

    @GetMapping("/user/info")
    public UserInfoResponseDto userInfo(Authentication authentication){

//        UserDetails principal = (UserDetails) authentication.getPrincipal();

//        return babyService.babyInfoService(babyId,principal.getUsername());
        Integer userTestId = 1;
        return userService.userInfo(userTestId);
    }

    @PatchMapping(value = "/user/info/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UserInfoUpdateResponseDto userInfoUpdate(Authentication authentication,
                                                    @RequestPart(value = "userImage",required = false) MultipartFile userImage,
                                                    @RequestPart(value="request") UserInfoUpdateRequestDto request)
            throws IOException {
        log.info(request.getUserNickname());

//        UserDetails principal = (UserDetails) authentication.getPrincipal();

//        return babyService.babyInfoService(babyId,principal.getUsername());
        Integer userTestId = 1;
        return userService.userInfoUpdate(userTestId,request,userImage);
    }

}

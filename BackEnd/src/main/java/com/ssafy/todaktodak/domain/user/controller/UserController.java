package com.ssafy.todaktodak.domain.user.controller;

import com.ssafy.todaktodak.domain.user.dto.*;
import com.ssafy.todaktodak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/info")
    public UserInfoResponseDto userInfo(Authentication authentication){

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return userService.userInfo(principal.getUsername());
    }

    @PatchMapping(value = "/user/info/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UserInfoUpdateResponseDto userInfoUpdate(Authentication authentication,
                                                    @RequestPart(value = "userImage",required = false) MultipartFile userImage,
                                                    @RequestPart(value="request") UserInfoUpdateRequestDto request)
            throws IOException {
        log.info(request.getUserNickname());

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return userService.userInfoUpdate(principal.getUsername(),request,userImage);
    }

    @PatchMapping(value = "/user/fcmKey", consumes = {MediaType.APPLICATION_JSON_VALUE })
    public UserFcmUpdateResponseDto userFcmEdit(Authentication authentication,
                                              @RequestBody() UserFcmUpdateRequestDto request)
            throws IOException {

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return userService.userFcmEdit(principal.getUsername(),request);
    }

}

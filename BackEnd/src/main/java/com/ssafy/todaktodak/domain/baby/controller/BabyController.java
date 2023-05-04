package com.ssafy.todaktodak.domain.baby.controller;

import com.ssafy.todaktodak.domain.baby.dto.BabyInfoResponseDto;
import com.ssafy.todaktodak.domain.baby.dto.BabyUpdateRequestDto;
import com.ssafy.todaktodak.domain.baby.service.BabyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BabyController {

    private final BabyService babyService;

    @GetMapping("/baby/info/{babyId}")
    public BabyInfoResponseDto BabyInfo(Authentication authentication, @PathVariable("babyId") Integer babyId){

//        UserDetails principal = (UserDetails) authentication.getPrincipal();

//        return babyService.babyInfoService(babyId,principal.getUsername());
        String userTestId = String.valueOf(1);
        return babyService.babyInfoService(babyId, userTestId);
    }

    @PatchMapping(value = "/baby/info/update/{babyId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public BabyInfoResponseDto BabyInfoUpdate(Authentication authentication,
                                              @PathVariable("babyId") Integer babyId,
                                              @RequestPart(value = "babyImage",required = false) MultipartFile babyImage,
                                              @RequestPart(value="request")  BabyUpdateRequestDto babyUpdateRequestDto)
            throws IOException {
        log.info(babyImage.toString());
        log.info(babyUpdateRequestDto.toString());
//        UserDetails principal = (UserDetails) authentication.getPrincipal();
//
//        return babyService.babyInfoUpdateService(babyId,babyImage,babyUpdateRequestDto,principal.getUsername());
        String userTestId = String.valueOf(1);
        return babyService.babyInfoUpdateService(babyId,babyImage,babyUpdateRequestDto,userTestId);
    }



}

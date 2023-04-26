package com.ssafy.todaktodak.domain.baby.controller;

import com.ssafy.todaktodak.domain.baby.dto.BabyInfoResponseDto;
import com.ssafy.todaktodak.domain.baby.service.BabyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class BabyController {

    private final BabyService babyService;

    @GetMapping("/baby/info/{babyId}")
    public BabyInfoResponseDto BabyInfo(Authentication authentication,@RequestParam Integer babyId){

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Integer test = 123;

        return babyService.babyInfoService(babyId,test);
    }



}

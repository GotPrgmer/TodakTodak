package com.ssafy.todaktodak.domain.device.controller;

import com.ssafy.todaktodak.domain.baby.dto.BabyInfoResponseDto;
import com.ssafy.todaktodak.domain.baby.service.BabyService;
import com.ssafy.todaktodak.domain.device.dto.DeviceInfoResponseDto;
import com.ssafy.todaktodak.domain.device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeviceController {

    private final DeviceService deviceService;


    @GetMapping("/device/info/{babyId}")
    public DeviceInfoResponseDto babyInfo(@PathVariable("babyId") Integer babyId){

        return deviceService.deviceInfo(babyId);
    }

}

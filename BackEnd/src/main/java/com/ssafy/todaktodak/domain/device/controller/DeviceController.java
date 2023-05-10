package com.ssafy.todaktodak.domain.device.controller;

import com.ssafy.todaktodak.domain.device.dto.DeviceInfoResponseDto;
import com.ssafy.todaktodak.domain.device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public DeviceInfoResponseDto deviceInfo(@PathVariable("babyId") Integer babyId){

        return deviceService.deviceInfo(babyId);
    }

}

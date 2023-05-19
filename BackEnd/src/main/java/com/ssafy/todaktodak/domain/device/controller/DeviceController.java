package com.ssafy.todaktodak.domain.device.controller;

import com.ssafy.todaktodak.domain.device.dto.DeviceAlarmRequestDto;
import com.ssafy.todaktodak.domain.device.dto.DeviceAlarmResponseDto;
import com.ssafy.todaktodak.domain.device.dto.DeviceInfoResponseDto;
import com.ssafy.todaktodak.domain.device.dto.DeviceInfoUpdateRequestDto;
import com.ssafy.todaktodak.domain.device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PatchMapping("/device/info/update/{babyId}")
    public DeviceInfoResponseDto deviceInfoUpdate(@PathVariable("babyId") Integer babyId,@RequestBody DeviceInfoUpdateRequestDto deviceInfoUpdateRequestDto){

        return deviceService.deviceInfoUpdate(babyId,deviceInfoUpdateRequestDto);
    }

    @PostMapping("/device/alarm")
    public DeviceAlarmResponseDto deviceAlarm(@RequestBody DeviceAlarmRequestDto request) throws IOException {
        return deviceService.deviceAlarm(request);
    }

}

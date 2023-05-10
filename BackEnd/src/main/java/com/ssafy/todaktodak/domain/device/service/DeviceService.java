package com.ssafy.todaktodak.domain.device.service;

import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.device.dto.DeviceInfoResponseDto;
import com.ssafy.todaktodak.domain.device.repository.DeviceRepository;
import com.ssafy.todaktodak.global.error.CustomException;
import com.ssafy.todaktodak.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;


    @Transactional
    public DeviceInfoResponseDto deviceInfo(Integer babyId){
        //babyId로 아기 조회
        Optional<Device> device = deviceRepository.findByBabyBabyId(babyId);
        if ( device.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        Device findDevice = device.get();

        return DeviceInfoResponseDto.ofDevice(findDevice);
        //
    }
}

package com.ssafy.todaktodak.domain.device.service;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.repository.BabyRepository;
import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.device.dto.DeviceAlarmRequestDto;
import com.ssafy.todaktodak.domain.device.dto.DeviceAlarmResponseDto;
import com.ssafy.todaktodak.domain.device.dto.DeviceInfoResponseDto;
import com.ssafy.todaktodak.domain.device.repository.DeviceRepository;
import com.ssafy.todaktodak.domain.user.domain.User;
import com.ssafy.todaktodak.domain.user.repository.UserRepository;
import com.ssafy.todaktodak.global.error.CustomException;
import com.ssafy.todaktodak.global.error.ErrorCode;
import com.ssafy.todaktodak.global.firebase.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final BabyRepository babyRepository;

    private final UserRepository userRepository;

    private final FirebaseService firebaseService;



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

    @Transactional
    public DeviceAlarmResponseDto deviceAlarm(DeviceAlarmRequestDto request) throws IOException {
        Optional<Device> device = deviceRepository.findBySerialNumber(request.getSerialNumber());
        if ( device.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        Device findDevice = device.get();

        Optional<Baby> baby = babyRepository.findById(findDevice.getBaby().getBabyId());
        if ( baby.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        Baby findBaby = baby.get();

        Optional<User> user = userRepository.findUserByUserId(findBaby.getUser().getUserId());
        if ( user.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        User findUser = user.get();

        firebaseService.sendMessageTo(findUser.getFcmKey(),request.getAlarmType(), request.getMessage());

        return DeviceAlarmResponseDto.of(request.getMessage(), findUser.getFcmKey());



    }
}

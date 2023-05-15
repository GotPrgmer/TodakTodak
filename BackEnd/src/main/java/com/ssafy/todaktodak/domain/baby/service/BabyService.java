package com.ssafy.todaktodak.domain.baby.service;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.dto.BabyAddRequestDto;
import com.ssafy.todaktodak.domain.baby.dto.BabyInfoResponseDto;
import com.ssafy.todaktodak.domain.baby.dto.BabyUpdateRequestDto;
import com.ssafy.todaktodak.domain.baby.repository.BabyRepository;
import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.device.repository.DeviceRepository;
import com.ssafy.todaktodak.domain.user.domain.User;
import com.ssafy.todaktodak.domain.user.repository.UserRepository;
import com.ssafy.todaktodak.global.error.CustomException;
import com.ssafy.todaktodak.global.error.ErrorCode;
import com.ssafy.todaktodak.global.storage.S3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;

import static com.ssafy.todaktodak.global.error.ErrorCode.BIRTH_DATE_INVALID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BabyService {

    private final BabyRepository babyRepository;

    private final UserRepository userRepository;

    private final DeviceRepository deviceRepository;
    private final S3Client s3Client;

    @Value("${s3-baby-default-image}")
    private String S3_BABY_IMAGE;
    @Value("${s3-default-image}")
    private String DEFAULT_IMAGE_S3;

    @Transactional
    public BabyInfoResponseDto babyInfo(Integer babyId,String userId){
        Integer userIdToInteger = Integer.parseInt(userId);
        //babyId로 아기 조회
        Optional <Baby> baby = babyRepository.findByBabyIdAndUserUserId(babyId,userIdToInteger);
        if ( baby.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        Baby findBaby = baby.get();
        Integer year= findBaby.getBabyBirthYear();
        Integer month = findBaby.getBabyBirthMonth();
        Integer day = findBaby.getBabyBirthDay();
        log.info(year.toString());
        log.info(month.toString());
        log.info(day.toString());
        Integer babyDDay = findDDay(year,month,day).orElseThrow(()-> new CustomException(BIRTH_DATE_INVALID));;


        return BabyInfoResponseDto.ofBaby(findBaby,babyDDay);
        //
    }

    @Transactional
    public BabyInfoResponseDto babyInfoUpdate(Integer babyId, MultipartFile file, BabyUpdateRequestDto babyUpdateRequestDto, String userId) throws IOException {
        //babyId로 아기 조회
        Integer userIdToInteger = Integer.parseInt(userId);
        Optional <Baby> baby = babyRepository.findByBabyIdAndUserUserId(babyId,userIdToInteger);
        if ( baby.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }

        Baby findBaby = baby.get();
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            String preImg = findBaby.getBabyImageUrl();
            if (preImg != null && !preImg.startsWith(this.DEFAULT_IMAGE_S3)) {
                s3Client.deleteFile(preImg);
            }
            imageUrl = s3Client.uploadFile(file);
        } else {
            imageUrl = findBaby.getBabyImageUrl();
        }
        Integer year = babyUpdateRequestDto.getBabyBirthYear();
        Integer month = babyUpdateRequestDto.getBabyBirthMonth();
        Integer day = babyUpdateRequestDto.getBabyBirthDay();

        // 별자리 찾기
        String babyConstellation = findConstellation(month, day).orElseThrow(()-> new CustomException(BIRTH_DATE_INVALID));;
        log.info(babyConstellation);
        // 띠 찾기
        String babyZodiac = findZodiac(year).orElseThrow(()-> new CustomException(BIRTH_DATE_INVALID));;
        log.info(babyZodiac);
        // dday 계산
        Integer babyDDay = findDDay(year, month, day).orElseThrow(() -> new CustomException(BIRTH_DATE_INVALID));
        log.info(String.valueOf(babyDDay));
        findBaby.updateBaby(babyUpdateRequestDto,babyConstellation,babyZodiac,imageUrl);
        return BabyInfoResponseDto.ofBaby(findBaby,babyDDay);
    }

    @Transactional
    public BabyInfoResponseDto babyAdd(MultipartFile file, BabyAddRequestDto babyAddRequestDto, String userId) throws IOException {
        //사용자 조회
        Integer userIdToInteger = Integer.parseInt(userId);
        Optional<User> user = userRepository.findById(userIdToInteger);
        if ( user.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        User findUser = user.get();
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = s3Client.uploadFile(file);
        } else {
            imageUrl = S3_BABY_IMAGE;
        }


        Integer year = babyAddRequestDto.getBabyBirthYear();
        Integer month = babyAddRequestDto.getBabyBirthMonth();
        Integer day = babyAddRequestDto.getBabyBirthDay();

        // 별자리 찾기
        String babyConstellation = findConstellation(month, day).orElseThrow(()-> new CustomException(BIRTH_DATE_INVALID));;
        log.info(babyConstellation);
        // 띠 찾기
        String babyZodiac = findZodiac(year).orElseThrow(()-> new CustomException(BIRTH_DATE_INVALID));;
        log.info(babyZodiac);
        // dday 계산
        Integer babyDDay = findDDay(year, month, day).orElseThrow(() -> new CustomException(BIRTH_DATE_INVALID));
        log.info(String.valueOf(babyDDay));
        Baby newBaby = Baby.newBabyAdd(findUser,babyAddRequestDto,babyConstellation,babyZodiac,imageUrl);
        babyRepository.save(newBaby);
        Device newDevice =  Device.newDeviceCreate(newBaby);
        deviceRepository.save(newDevice);
        return BabyInfoResponseDto.ofBaby(newBaby,babyDDay);
    }

    public Optional<String> findConstellation(Integer month,Integer day){
        if (month == null || day == null) {
            return Optional.empty();
        }
        String[] constellationSigns = {
                "염소", "물병", "물고기", "양", "황소", "쌍둥이",
                "게", "사자", "처녀", "천칭", "전갈", "사수", "염소"
        };
        int[] endDates = {20, 19, 21, 20, 21, 22, 23, 23, 24, 23, 23, 25};

        int zodiacIndex = month - (day < endDates[month - 1] ? 1 : 0);
        return Optional.of(constellationSigns[zodiacIndex]);

    }

    public Optional<String> findZodiac(Integer year){
        if (year == null || 1900>year){
            return Optional.empty();
        }
        String[] zodiacSigns = {
                "원숭이", "닭", "개", "돼지", "쥐", "소",
                "호랑이", "토끼", "용", "뱀", "말", "양"
        };
        int temp = year % 12;

        return Optional.of(zodiacSigns[temp]);

    }

    public Optional<Integer> findDDay(Integer year,Integer month, Integer day){
        if (year == null || month == null || day == null){
            return Optional.empty();
        }

        Calendar birthDay = Calendar.getInstance();
        birthDay.set(year, month, day);


        // 아기 생일 날짜로 밀리타임 계산
        long dayFromMill = 1000 * 60 * 60 * 24;
        LocalDateTime birtDayDateTime = LocalDateTime.of(year,month,day,0,0);
        Instant birthInstant = birtDayDateTime.atZone(ZoneId.systemDefault()).toInstant();
        long birthEpochMilli = birthInstant.toEpochMilli();

        // 현재 날짜로 밀리타임계산
        LocalDateTime currentDateTime = LocalDateTime.now();
        Instant currentInstant = currentDateTime.atZone(ZoneId.systemDefault()).toInstant();
        long currentEpochMilli = currentInstant.toEpochMilli();

        Integer dDay = Math.toIntExact((currentEpochMilli - birthEpochMilli) / dayFromMill);
        log.info(String.valueOf(dDay));
        if (dDay <0){
            return Optional.empty();
        }

        return Optional.of(dDay);

    }


}


package com.ssafy.todaktodak.domain.baby.service;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.dto.BabyInfoResponseDto;
import com.ssafy.todaktodak.domain.baby.dto.BabyUpdateRequestDto;
import com.ssafy.todaktodak.domain.baby.repository.BabyRepository;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BabyService {

    private final BabyRepository babyRepository;

    private final S3Client s3Client;


    @Value("${s3-default-image}")
    private String DEFAULT_IMAGE_S3;

    @Transactional
    public BabyInfoResponseDto babyInfoService(Integer babyId,String userId){
        Integer userIdToNumber = Integer.parseInt(userId);
        //babyId로 아기 조회
        Optional <Baby> baby = babyRepository.findByBabyIdAndUserUserId(babyId,userIdToNumber);
        if ( baby.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        Baby findBaby = baby.get();

        return BabyInfoResponseDto.ofBaby(findBaby);
        //
    }

    @Transactional
    public BabyInfoResponseDto babyInfoUpdateService(Integer babyId, MultipartFile file, BabyUpdateRequestDto babyUpdateRequestDto, String userId) throws IOException {
        //babyId로 아기 조회
        Integer userIdToNumber = Integer.parseInt(userId);
        Optional <Baby> baby = babyRepository.findByBabyIdAndUserUserId(babyId,userIdToNumber);
        System.out.println(baby.get());
        if ( baby.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }

        Baby findBaby = baby.get();
        String imageUrl = null;
        if (!file.isEmpty()) {
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
        String babyConstellation = findConstellation(month, day);
        log.info(babyConstellation);
        // 띠 찾기
        String babyZodiac = findZodiac(year);
        log.info(babyZodiac);
        // dday 계산
        Integer babyDDay = findDDay(year,month,day);
        log.info(String.valueOf(babyDDay));
        findBaby.updateBaby(babyUpdateRequestDto,babyConstellation,babyZodiac,babyDDay,imageUrl);
        return BabyInfoResponseDto.ofBaby(findBaby);
    }

    public String findConstellation(Integer month,Integer day){
        String[] constellationSigns = {
                "염소", "물병", "물고기", "양", "황소", "쌍둥이",
                "게", "사자", "처녀", "천칭", "전갈", "사수", "염소"
        };
        int[] endDates = {20, 19, 21, 20, 21, 22, 23, 23, 24, 23, 23, 25};

        int zodiacIndex = month - (day < endDates[month - 1] ? 1 : 0);
        return constellationSigns[zodiacIndex];

    }

    public String findZodiac(Integer year){
        if (year == null || 1900>year){
            throw new IllegalArgumentException();
        }
        String[] zodiacSigns = {
                "원숭이", "닭", "개", "돼지", "쥐", "소",
                "호랑이", "토끼", "용", "뱀", "말", "양"
        };
        int temp = year % 12;

        return zodiacSigns[temp];

    }

    public Integer findDDay(Integer year,Integer month, Integer day){

        Calendar birthDay = Calendar.getInstance();
        birthDay.set(year, month, day);

        long birthDayToMills = birthDay.getTimeInMillis();
        long dayFromMill = 1000 * 60 * 60 * 24;
        long now = System.currentTimeMillis();


        return Math.toIntExact((now - birthDayToMills) / dayFromMill);

    }


}

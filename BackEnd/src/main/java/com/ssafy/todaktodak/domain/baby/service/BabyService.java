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
        findBaby.updateBaby(babyUpdateRequestDto,imageUrl);

        return BabyInfoResponseDto.ofBaby(findBaby);



        //
    }

}

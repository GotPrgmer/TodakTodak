package com.ssafy.todaktodak.domain.baby.service;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.dto.BabyInfoResponseDto;
import com.ssafy.todaktodak.domain.baby.repository.BabyRepository;
import com.ssafy.todaktodak.global.error.CustomException;
import com.ssafy.todaktodak.global.error.ErrorCode;
import com.ssafy.todaktodak.global.error.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BabyService {

    private final BabyRepository babyRepository;

    public BabyInfoResponseDto babyInfoService(Integer babyId,String userId){
        Integer userIdToNumber = Integer.parseInt(userId);
        //babyId로 아기 조회
        Optional <Baby> baby = babyRepository.findByBabyIdAndUserUserId(babyId,userIdToNumber);
        Baby findBaby = null;
        if ( baby.isPresent()) {
            findBaby = baby.get();
        }
        else{
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }

        return BabyInfoResponseDto.ofBaby(findBaby);



        //
    }
}

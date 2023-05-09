package com.ssafy.todaktodak.domain.cry.service;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.repository.BabyRepository;
import com.ssafy.todaktodak.domain.cry.domain.Cry;
import com.ssafy.todaktodak.domain.cry.dto.CryLoggingRequestDto;
import com.ssafy.todaktodak.domain.cry.dto.CryLoggingResponseDto;
import com.ssafy.todaktodak.domain.cry.dto.CryRecordingRequestDto;
import com.ssafy.todaktodak.domain.cry.dto.CryRecordingResponseDto;
import com.ssafy.todaktodak.domain.cry.repository.CryRepository;
import com.ssafy.todaktodak.global.error.CustomException;
import com.ssafy.todaktodak.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryService {

    private final BabyRepository babyRepository;

    private final CryRepository cryRepository;

    public CryRecordingResponseDto cryRecording(CryRecordingRequestDto cryRecordingRequestDto){
        Instant cryStartTimeToInstant = Instant.ofEpochMilli(cryRecordingRequestDto.getCryStartDate());
        Instant cryEndTimeToInstant = Instant.ofEpochMilli(cryRecordingRequestDto.getCryEndDate());

        long durationInMillis = cryRecordingRequestDto.getCryEndDate() - cryRecordingRequestDto.getCryStartDate();

        long durationInSeconds = durationInMillis / 1000;
        Integer babyId = Integer.parseInt(cryRecordingRequestDto.getBabyId());

        Optional<Baby> baby = babyRepository.findById(babyId);


        if ( baby.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }

        Baby findBaby = baby.get();


        LocalDateTime cryStartTimeToLocalDateTime = cryStartTimeToInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDateTime cryEndTimeToLocalDateTime = cryEndTimeToInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        Cry newCry = Cry.newCryRecordCreate(findBaby, cryStartTimeToLocalDateTime, cryEndTimeToLocalDateTime, durationInSeconds);
        cryRepository.save(newCry);

        return CryRecordingResponseDto.ofCry(newCry);




    }

//    public CryLoggingResponseDto cryLogging(CryLoggingRequestDto cryLoggingRequestDto){
//
//        Integer year = cryLoggingRequestDto.getYear();
//        Integer month = cryLoggingRequestDto.getMonth();
//        Integer day = cryLoggingRequestDto.getDay();
//
//
//        Integer babyId = Integer.parseInt(cryLoggingRequestDto.getBabyId());
//
//        LocalDateTime endDateTime = LocalDateTime.of(year, month, day, 23, 59, 59);
//        LocalDateTime startDateTime = endDateTime.minusDays(6)
//                .withHour(0)
//                .withMinute(0)
//                .withSecond(0);
//
//        Optional<Baby> baby = babyRepository.findById(babyId);
//
//
//        if ( baby.isEmpty()) {
//            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
//        }
//
//        Baby findBaby = baby.get();
//        List<Cry> cryLogList = cryRepository.findAllByBabyIdAndCryStartDateBetween(babyId, startDateTime, endDateTime);
//        //cryLogList를 날짜별로 리스트를 만들어서 해당 리스트에 add를 해서 마지막에 dto에 넣어주는 방식으로 하면될듯
//
//
//        return CryLoggingResponseDto.ofCry(newCry);
//
//
//
//
//    }


}

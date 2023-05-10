package com.ssafy.todaktodak.domain.cry.service;

import com.google.gson.Gson;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
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

    public ResponseEntity<String> cryLogging(Integer babyId, Integer year, Integer month, Integer day){

//        Integer year = cryLoggingRequestDto.getYear();
//        Integer month = cryLoggingRequestDto.getMonth();
//        Integer day = cryLoggingRequestDto.getDay();


        Integer babyIdToInteger = babyId;

        LocalDateTime endDateTime = LocalDateTime.of(year, month, day, 23, 59, 59);
        LocalDateTime startDateTime = endDateTime.minusDays(6)
                .withHour(0)
                .withMinute(0)
                .withSecond(0);

        Optional<Baby> baby = babyRepository.findById(babyIdToInteger);


        if ( baby.isEmpty()) {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }

        Baby findBaby = baby.get();
        List<Cry> cryLogList = cryRepository.findAllByBabyAndCryStartDateBetween(baby.get(), startDateTime, endDateTime);
        //cryLogList를 날짜별로 리스트를 만들어서 해당 리스트에 add를 해서 마지막에 dto에 넣어주는 방식으로 하면될듯
        Map<String, Object> temp = new HashMap<>();
        for (Cry cry : cryLogList){
            String tempDate = cry.getCryCreatedDate().toString().substring(0,10); //날짜
            LinkedHashSet hs = new LinkedHashSet();
//            log.info(tempDate);
//            log.info(cry.getCryCreatedDate().toString());
            for (Cry c : cryLogList){  //순회를 돌면서
                if (c.getCryCreatedDate().toString().substring(0, 10).equals(tempDate)){ //날짜가 같다면
                    hs.add(Arrays.asList(
                            c.getCryTime().toString(), c.getCryStartDate().toString().substring(11,16), c.getCryEndDate().toString().substring(11,16)
                    ));   // hashset에 담아보자
                }
            }
//            log.info(hs.toString());
            Iterator<Object> iter = hs.iterator();
            ArrayList arr = new ArrayList();
            while(iter.hasNext()){
//                log.info("lists in set");
//                log.info(iter.next().toString());
                arr.add(iter.next());
            }

            temp.put(cry.getCryStartDate().toString().substring(0,10), arr);
//            log.info(Integer.toString(hs.size()));
//            temp.put("date", cry.getCryStartDate().toString().substring(0, 11));
        }
//        log.info(temp.toString());
        ArrayList logs = new ArrayList<>();

        temp.forEach((k,v) -> {
            Map<String, Object> cryLog = new HashMap<>();
            ArrayList<Cry> cryList = (ArrayList<Cry>) v;
            cryLog.put("date", k);
            cryLog.put("log", v);
            cryLog.put("cryCounts", cryList.size() );
//            log.info(cryLog.toString());
            logs.add(cryLog);
        }) ;
//        log.info(logs.toString());

        Map<String, ArrayList> res = new HashMap<>();
        res.put("cry_log", logs);

        Gson gson = new Gson();
        String json = gson.toJson(res);
//        log.info(json);
        return new ResponseEntity<String>(
                json, HttpStatus.OK);
    }
}

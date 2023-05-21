package com.ssafy.todaktodak.domain.cry.service;

import com.google.gson.Gson;
import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.repository.BabyRepository;
import com.ssafy.todaktodak.domain.cry.domain.Cry;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
            throw new CustomException(ErrorCode.BABY_NOT_FOUND);
        }

        Baby findBaby = baby.get();


        LocalDateTime cryStartTimeToLocalDateTime = cryStartTimeToInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDateTime cryEndTimeToLocalDateTime = cryEndTimeToInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        Cry newCry = Cry.newCryRecordCreate(findBaby, cryStartTimeToLocalDateTime, cryEndTimeToLocalDateTime, durationInSeconds);
        cryRepository.save(newCry);

        return CryRecordingResponseDto.ofCry(newCry);

    }

    public ResponseEntity<String> cryLogging(Integer babyId, Integer year, Integer month, Integer day){



        Integer babyIdToInteger = babyId;

        LocalDateTime endDateTime = LocalDateTime.of(year, month, day, 23, 59, 59);
        LocalDateTime startDateTime = endDateTime.minusDays(4)
                .withHour(0)
                .withMinute(0)
                .withSecond(0);

        Optional<Baby> baby = babyRepository.findById(babyIdToInteger);


        if ( baby.isEmpty()) {
            throw new CustomException(ErrorCode.BABY_NOT_FOUND);
        }

        Baby findBaby = baby.get();
        List<Cry> cryLogList = cryRepository.findAllByBabyAndCryStartDateBetween(findBaby, startDateTime, endDateTime);
        //cryLogList를 날짜별로 리스트를 만들어서 해당 리스트에 add를 해서 마지막에 dto에 넣어주는 방식으로 하면될듯
        Map<String, Object> temp = new HashMap<>();
        for (Cry cry : cryLogList){
            String tempDate = cry.getCryCreatedDate().toString().substring(0,10); //날짜
            LinkedHashSet hs = new LinkedHashSet();
            for (Cry c : cryLogList){  //순회를 돌면서
                if (c.getCryCreatedDate().toString().substring(0, 10).equals(tempDate)){ //날짜가 같다면
                    hs.add(Arrays.asList(
                            c.getCryTime().toString(), c.getCryStartDate().toString().substring(11,16), c.getCryEndDate().toString().substring(11,16)
                    ));   // hashset에 담아보자
                }
            }
            Iterator<Object> iter = hs.iterator();
            ArrayList arr = new ArrayList();
            while(iter.hasNext()){
                arr.add(iter.next());
            }

            temp.put(cry.getCryStartDate().toString().substring(0,10), arr);
        }
        ArrayList<Map<String, Object>> logs = new ArrayList<Map<String, Object>>();

        temp.forEach((k,v) -> {
            Map<String, Object> cryLog = new HashMap<>();
            ArrayList<Cry> cryList = (ArrayList<Cry>) v;
            cryLog.put("date", k);
            cryLog.put("log", v);
            cryLog.put("cryCounts", cryList.size() );
            logs.add(cryLog);
        }) ;


        for(int i = 0; i <=4; i++){
            String tempDate = endDateTime.minusDays(i).toString().substring(0,10);
            Boolean flag = false;
            for(Map<String, Object> e : logs){
                if (Objects.equals(e.get("date").toString(), tempDate)){
                    flag = true;
                }
            }
            if (!flag){
                List<String> list = Collections.emptyList();
                Map<String, Object> cryLog = new HashMap<>();
                cryLog.put("date", tempDate);
                cryLog.put("log", list);
                cryLog.put("cryCounts", 0 );
                logs.add(cryLog);
            }
        }

        Collections.sort(logs, new Comparator<Map<String, Object>>() {
            //
            public int compare(Map<String, Object> log1, Map<String, Object> log2) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");;
                LocalDate date1 = LocalDate.parse((String)log1.get("date"), formatter);
                LocalDate date2 = LocalDate.parse((String)log2.get("date"), formatter);
                return date1.compareTo(date2);
            }
        });


        Map<String, ArrayList> res = new HashMap<>();
        res.put("cry_log", logs);

        Gson gson = new Gson();
        String json = gson.toJson(res);
        return new ResponseEntity<String>(
                json, HttpStatus.OK);
    }
}
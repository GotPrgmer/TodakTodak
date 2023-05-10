package com.ssafy.todaktodak.domain.cry.controller;

import com.ssafy.todaktodak.domain.cry.dto.CryLoggingRequestDto;
import com.ssafy.todaktodak.domain.cry.dto.CryLoggingResponseDto;
import com.ssafy.todaktodak.domain.cry.dto.CryRecordingRequestDto;
import com.ssafy.todaktodak.domain.cry.dto.CryRecordingResponseDto;
import com.ssafy.todaktodak.domain.cry.service.CryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CryController {
    private final CryService cryService;

    @PostMapping(value = "/cry/record")
    public CryRecordingResponseDto cryRecording(@RequestBody CryRecordingRequestDto cryRecordingRequestDto){
        log.info(cryRecordingRequestDto.getBabyId().toString());
        return cryService.cryRecording(cryRecordingRequestDto);
    }

    @GetMapping(value = "/cry/logging")
    public ResponseEntity<String> cryLogging(@RequestParam Integer babyId, @RequestParam Integer year, @RequestParam Integer month, @RequestParam Integer day){
        return cryService.cryLogging(babyId, year, month, day );
    }

}

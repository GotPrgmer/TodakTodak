package com.ssafy.todaktodak.domain.cry.controller;

import com.ssafy.todaktodak.domain.cry.dto.CryRecordingRequestDto;
import com.ssafy.todaktodak.domain.cry.dto.CryRecordingResponseDto;
import com.ssafy.todaktodak.domain.cry.service.CryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

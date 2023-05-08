package com.ssafy.todaktodak.domain.cry.controller;

import com.ssafy.todaktodak.domain.cry.dto.CryRecordingRequestDto;
import com.ssafy.todaktodak.domain.cry.dto.CryRecordingResponseDto;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class CryController {

    @PostMapping(value = "cry", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CryRecordingResponseDto cryRecording(Authentication authentication,
                                                @RequestBody CryRecordingRequestDto cryRecordingRequestDto){
//        UserDetails principal = (UserDetails) authentication.getPrincipal();
//        String userTestId = String.valueOf(1);
        System.out.println("crtRecodingRequestDto" + cryRecordingRequestDto);
        return null;
    }
}

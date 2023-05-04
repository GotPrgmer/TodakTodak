package com.ssafy.todaktodak.domain.cry.controller;

import com.ssafy.todaktodak.domain.cry.dto.CryRecordingRequestDto;
import com.ssafy.todaktodak.domain.cry.dto.CryRecordingResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class CryController {

//    @PostMapping
//    public CryRecordingResponseDto cryRecording(Authentication authentication, @RequestBody CryRecordingRequestDto cryRecordingRequestDto){
//        UserDetails principal = (UserDetails) authentication.getPrincipal();
//    }
}

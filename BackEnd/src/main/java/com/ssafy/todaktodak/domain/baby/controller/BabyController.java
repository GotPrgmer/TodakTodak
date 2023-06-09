package com.ssafy.todaktodak.domain.baby.controller;

import com.ssafy.todaktodak.domain.baby.dto.BabyAddRequestDto;
import com.ssafy.todaktodak.domain.baby.dto.BabyInfoResponseDto;
import com.ssafy.todaktodak.domain.baby.dto.BabyUpdateRequestDto;
import com.ssafy.todaktodak.domain.baby.service.BabyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BabyController {

    private final BabyService babyService;

    @GetMapping("/baby/info/{babyId}")
    public BabyInfoResponseDto babyInfo(Authentication authentication, @PathVariable("babyId") Integer babyId) {

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return babyService.babyInfo(babyId, principal.getUsername());
    }

    @PatchMapping(value = "/baby/info/update/{babyId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public BabyInfoResponseDto babyInfoUpdate(Authentication authentication,
                                              @PathVariable("babyId") Integer babyId,
                                              @RequestPart(value = "babyImage", required = false) MultipartFile babyImage,
                                              @RequestPart(value = "request") BabyUpdateRequestDto babyUpdateRequestDto)
            throws IOException {

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return babyService.babyInfoUpdate(babyId, babyImage, babyUpdateRequestDto, principal.getUsername());
    }

    @PostMapping(value = "/baby/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public BabyInfoResponseDto babyAdd(Authentication authentication,
                                       @RequestPart(value = "babyImage", required = false) MultipartFile babyImage,
                                       @RequestPart(value = "request") BabyAddRequestDto babyAddRequestDto)
            throws IOException {

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return babyService.babyAdd(babyImage, babyAddRequestDto, principal.getUsername());
    }


}

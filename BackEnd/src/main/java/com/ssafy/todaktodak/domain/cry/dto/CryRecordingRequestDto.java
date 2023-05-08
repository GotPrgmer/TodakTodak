package com.ssafy.todaktodak.domain.cry.dto;


import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class CryRecordingRequestDto {

    private String babyId;

    private Long cryStartDate;
    private Long cryEndDate;


}

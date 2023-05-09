package com.ssafy.todaktodak.domain.cry.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class CryRecordingRequestDto {

    @JsonProperty("babyId")
    private String babyId;

    @JsonProperty("cryStartDate")
    private Long cryStartDate;

    @JsonProperty("cryEndDate")
    private Long cryEndDate;


}

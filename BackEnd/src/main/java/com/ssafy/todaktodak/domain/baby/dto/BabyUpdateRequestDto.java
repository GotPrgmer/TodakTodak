package com.ssafy.todaktodak.domain.baby.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BabyUpdateRequestDto {

    private String babyNickname;

    private String babyName;


    private String babyGender;

    private Integer babyBirthYear;

    private Integer babyBirthMonth;

    private Integer babyBirthDay;



}

package com.ssafy.todaktodak.domain.baby.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class BabyUpdateRequestDto {

    private String babyNickname;

    private String babyName;


    private String babyGender;

    private Integer babyBirthYear;

    private Integer babyBirthMonth;

    private Integer babyBirthDay;



}

package com.ssafy.todaktodak.domain.baby.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class BabyUpdateRequestDto {

    @NotNull
    private String babyNickname;


    @NotNull
    private String babyGender;

    @NotNull
    private String babyBirthYear;

    @NotNull
    private String babyBirthMonth;

    @NotNull
    private String babyBirthDay;



}

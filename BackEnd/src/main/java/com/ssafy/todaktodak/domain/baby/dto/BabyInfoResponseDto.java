package com.ssafy.todaktodak.domain.baby.dto;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.baby.domain.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BabyInfoResponseDto {


    private Integer babyId;
    private String parentEmail;

    private String babyName;

    private String babyNickname;

    private String babyImageUrl;

    private String babyGender;

    private Integer babyBirthYear;

    private Integer babyBirthMonth;

    private Integer babyBirthDay;

    private Integer babyDDay;

    private String babyZodiak;

    private String babyConstellation;

    public static BabyInfoResponseDto ofBaby(Baby baby){

        return BabyInfoResponseDto.builder()
                .babyId(baby.getBabyId())
                .parentEmail(baby.getUser().getUserEmail())

                .babyDDay(baby.getBabyDDay())

                .babyNickname(baby.getBabyNickname())

                .babyImageUrl(baby.getBabyImageUrl())

                .babyGender(Gender.ofGender(baby.getBabyGender()))

                .babyBirthYear(baby.getBabyBirthYear())

                .babyBirthMonth(baby.getBabyBirthMonth())

                .babyBirthDay(baby.getBabyBirthDay())

                .babyBirthDay(baby.getBabyBirthDay())

                .babyZodiak(baby.getBabyZodiak())

                .babyConstellation(baby.getBabyConstellation())
                .babyName(baby.getBabyName())
                .build();

    }


}

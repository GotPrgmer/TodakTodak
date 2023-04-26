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

    private String babyNickname;

    private String babyImageUrl;

    private String babyGender;

    private String babyBirthYear;

    private String babyBirthMonth;

    private String babyBirthDay;

    private String babyJodiak;

    private String babyConstellation;

    public static BabyInfoResponseDto ofBaby(Baby baby){

        return BabyInfoResponseDto.builder()
                .babyId(baby.getBabyId())
                .parentEmail(baby.getUser().getUserEmail())

                .babyNickname(baby.getBabyNickname())

                .babyImageUrl(baby.getBabyImageUrl())

                .babyGender(Gender.ofGender(baby.getBabyGender()))

                .babyBirthYear(baby.getBabyBirthYear())

                .babyBirthMonth(baby.getBabyBirthMonth())

                .babyBirthDay(baby.getBabyBirthDay())

                .babyBirthDay(baby.getBabyBirthDay())

                .babyJodiak(baby.getBabyJodiak())

                .babyConstellation(baby.getBabyConstellation())
                .build();

    }


}

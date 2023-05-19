package com.ssafy.todaktodak.domain.baby.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum Gender {
    X("unknown"),M("male") ,F("female");



    private final String label;

    public static String ofGender(Gender gender) throws IllegalArgumentException {
        if (Gender.X == gender) {
            return Gender.X.getLabel();
        } else if (Gender.M == gender) {
            return Gender.M.getLabel();
        } else if (Gender.F == gender) {
            return Gender.F.getLabel();
        } else {
            throw new IllegalArgumentException("Invalid gender value: " + gender);
        }
    }

    public static Gender ofString(String gender) throws IllegalArgumentException {
        if (Gender.X.getLabel().equals(gender) ) {
            return Gender.X;
        } else if (Gender.M.getLabel().equals(gender)) {
            return Gender.M;
        } else if (Gender.F.getLabel().equals(gender)) {
            return Gender.F;
        }else{
                throw new IllegalArgumentException("Invalid gender value: " + gender);
            }
        }


    }




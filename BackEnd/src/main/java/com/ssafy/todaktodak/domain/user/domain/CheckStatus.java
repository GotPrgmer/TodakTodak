package com.ssafy.todaktodak.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckStatus {

    CHECKED(false), UNCHECKED(true);

    private final boolean value;

    public static CheckStatus ofValue(boolean value) {
        return value ? CHECKED : UNCHECKED;
    }

    public static boolean ofFlag(CheckStatus flag) {
        return CheckStatus.CHECKED == flag ? true : false;
    }
}

package com.ssafy.todaktodak.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeType {
    CRY(1),
    FLIP(2);

    private final int status;

}

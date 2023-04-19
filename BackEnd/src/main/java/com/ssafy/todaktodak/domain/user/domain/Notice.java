package com.ssafy.todaktodak.domain.user.domain;


import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @Column(name = "notice_id")
    private Integer noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "current_baby_id")
    @NotNull
    private Integer currentBabyId;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "notice_type")
    private NoticeType noticeType;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "check_status")
    private CheckStatus checkStatus;
}

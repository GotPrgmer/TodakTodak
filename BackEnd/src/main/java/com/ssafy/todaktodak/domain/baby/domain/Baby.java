package com.ssafy.todaktodak.domain.baby.domain;



import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.user.domain.User;
import com.sun.istack.NotNull;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "baby")
public class Baby {


    @Id
    @Column(name = "baby_id")
    private Integer babyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @NotNull
    @Column(name = "baby_nickname")
    private String babyNickname;

    @Column(name = "baby_created_date", updatable = false)
    @CreatedDate
    private LocalDateTime babyCreatedDate;


    @Column(name = "baby_updated_date")
    @LastModifiedDate
    private LocalDateTime babyUpdatedDate;


    @OneToOne(mappedBy = "baby")
    private Device device;

}


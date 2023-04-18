package com.ssafy.todaktodak.domain.baby.domain;



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
    @GeneratedValue
    @Column(name = "baby_id")
    private Integer babyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @NotNull
    @Column(name = "temperature",length = 4)
    private String temperature;

    @NotNull
    @Column(name = "humidity",length = 3)
    private String humidity;

    @NotNull
    @Column(name = "baby_nickname")
    private String babyNickname;

    @Column(name = "baby_created_date", updatable = false)
    @CreatedDate
    private LocalDateTime babyCreatedDate;


    @Column(name = "baby_updated_date")
    @LastModifiedDate
    private LocalDateTime babyUpdatedDate;

    @Builder.Default
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Baby> babys = new ArrayList<Baby>();
}


package com.ssafy.todaktodak.domain.device.domain;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.sun.istack.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="device")
public class Device {

    @Id
    @Column(name = "baby_id")
    private Integer babyId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "baby_id")
    private Baby baby;

    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_imageUrl")
    private String userImageUrl;

    @Column(name = "user_created_date", updatable = false)
    @CreatedDate
    private LocalDateTime userCreatedDate;


    @Column(name = "user_updated_date")
    @LastModifiedDate
    private LocalDateTime userUpdatedDate;





}

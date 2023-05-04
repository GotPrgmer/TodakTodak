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
    @Column(name = "device_id")
    private Integer deviceId;

    @OneToOne
    @JoinColumn(name = "baby_id")
    private Baby baby;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "session_token")
    private String sessionToken;


    @Column(name = "device_created_date", updatable = false)
    @CreatedDate
    private LocalDateTime userCreatedDate;


    @Column(name = "device_updated_date")
    @LastModifiedDate
    private LocalDateTime userUpdatedDate;





}

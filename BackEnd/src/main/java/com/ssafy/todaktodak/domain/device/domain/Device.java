package com.ssafy.todaktodak.domain.device.domain;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.sun.istack.NotNull;
import io.openvidu.java.client.Connection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "device")
public class Device {

    @Id
    @Column(name = "device_id")
    @GeneratedValue
    private Integer deviceId;

    @OneToOne
    @JoinColumn(name = "baby_id")
    private Baby baby;

    @NotNull
    @Column(name = "device_serial_number")
    private String deviceSerialNumber;

    @Column(name = "device_session_id")
    private String deviceSessionId;

    @Column(name = "device_connection_id")
    private String deviceConnectionId;


    @Column(name = "device_created_date", updatable = false)
    @CreatedDate
    private LocalDateTime deviceCreatedDate;


    @Column(name = "device_updated_date")
    @LastModifiedDate
    private LocalDateTime deviceUpdatedDate;

    public static Device newDeviceCreate(Baby baby){
        return Device.builder()
                .baby(baby)
                .deviceSerialNumber("todak"+baby.getBabyId())
                .build();
    }

    public void updateSessionId(String sessionId) {
        this.deviceSessionId = sessionId;
    }

    public void updateConnectionId(Connection connection){
        this.deviceConnectionId = connection.getConnectionId();
    }





}

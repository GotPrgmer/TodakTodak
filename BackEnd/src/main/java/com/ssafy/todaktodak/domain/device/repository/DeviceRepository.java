package com.ssafy.todaktodak.domain.device.repository;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.device.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Integer> {

    Optional<Device> findByDeviceId(Integer deviceId);

    Optional<Device> findByBabyBabyId(Integer babyId);

    Optional<Device> findBySessionId(String sessionId);

    Optional<Device> findBySerialNumber(String SerialNumber);

}

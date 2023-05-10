package com.ssafy.todaktodak.domain.device.repository;

import com.ssafy.todaktodak.domain.device.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Integer> {

    Optional<Device> findByDeviceId(Integer DeviceId);

    Optional<Device> findByBabyBabyId(Integer BabyId);

    Optional<Device> findBySessionId(String SessionId);

}

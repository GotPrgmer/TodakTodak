package com.ssafy.todaktodak.domain.cry.repository;

import com.ssafy.todaktodak.domain.cry.domain.Cry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CryRepository extends JpaRepository<Cry,Integer> {

    List<Cry> findAllByBabyIdAndCryStartDateBetween(Integer babyId, LocalDateTime start, LocalDateTime end);
}

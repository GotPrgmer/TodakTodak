package com.ssafy.todaktodak.domain.cry.repository;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.domain.cry.domain.Cry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface CryRepository extends JpaRepository<Cry, Integer> {

    List<Cry> findAllByBabyAndCryStartDateBetween(Baby baby, LocalDateTime startDateTime, LocalDateTime endDateTime);
}

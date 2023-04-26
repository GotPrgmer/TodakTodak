package com.ssafy.todaktodak.domain.baby.repository;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BabyRepository extends JpaRepository<Baby,Integer> {
    Optional<Baby> findByBabyIdAndUserUserId(Integer babyId,Integer userId);

}

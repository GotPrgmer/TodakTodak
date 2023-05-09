package com.ssafy.todaktodak.domain.baby.repository;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BabyRepository extends JpaRepository<Baby,Integer> {

    List<Baby> findBabiesByUserUserId(Integer userId);

    Optional<Baby> findByBabyIdAndUserUserId(Integer babyId,Integer userId);

}

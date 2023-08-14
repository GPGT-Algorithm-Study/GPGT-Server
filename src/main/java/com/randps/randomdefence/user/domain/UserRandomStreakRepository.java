package com.randps.randomdefence.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRandomStreakRepository extends JpaRepository<UserRandomStreak, Long> {
    Optional<UserRandomStreak> findByBojHandle(String bojHandle);
}

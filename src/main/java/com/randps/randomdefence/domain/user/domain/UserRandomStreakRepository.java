package com.randps.randomdefence.domain.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRandomStreakRepository extends JpaRepository<UserRandomStreak, Long> {
    Optional<UserRandomStreak> findByBojHandle(String bojHandle);
}

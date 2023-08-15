package com.randps.randomdefence.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGrassRepository extends JpaRepository<UserGrass, Long> {
    Optional<UserGrass> findByUserRandomStreakAndDate(UserRandomStreak userRandomStreak, String date);

    List<UserGrass> findAllByUserRandomStreak(UserRandomStreak userRandomStreak);

    Optional<UserGrass> findByUserRandomStreak(UserRandomStreak userRandomStreak);

    List<UserGrass> findAllByUserRandomStreakAndGrassInfo(UserRandomStreak userRandomStreak, Boolean grassInfo);
}

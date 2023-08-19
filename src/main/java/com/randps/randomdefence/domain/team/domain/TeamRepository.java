package com.randps.randomdefence.domain.team.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTeamName(String teamName);

    Optional<Team> findByTeamNumber(Integer teamNumber);
}

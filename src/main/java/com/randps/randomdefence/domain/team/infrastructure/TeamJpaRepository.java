package com.randps.randomdefence.domain.team.infrastructure;

import com.randps.randomdefence.domain.team.domain.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTeamName(String teamName);

    Optional<Team> findByTeamNumber(Integer teamNumber);
}

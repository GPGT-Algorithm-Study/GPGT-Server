package com.randps.randomdefence.domain.team.service.port;

import com.randps.randomdefence.domain.team.domain.Team;
import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    Optional<Team> findByTeamName(String teamName);

    Optional<Team> findByTeamNumber(Integer teamNumber);

    Team save(Team team);

    List<Team> findAll();
}

package com.randps.randomdefence.domain.team.infrastructure;

import com.randps.randomdefence.domain.team.domain.Team;
import com.randps.randomdefence.domain.team.service.port.TeamRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryAdapter implements TeamRepository {

    private final TeamJpaRepository teamJpaRepository;

    @Override
    public Optional<Team> findByTeamName(String teamName) {
        return teamJpaRepository.findByTeamName(teamName);
    }

    @Override
    public Optional<Team> findByTeamNumber(Integer teamNumber) {
        return teamJpaRepository.findByTeamNumber(teamNumber);
    }

    @Override
    public Team save(Team team) {
        return teamJpaRepository.save(team);
    }

    @Override
    public List<Team> findAll() {
        return teamJpaRepository.findAll();
    }
}

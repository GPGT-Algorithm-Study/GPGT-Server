package com.randps.randomdefence.domain.team.mock;

import com.randps.randomdefence.domain.team.domain.Team;
import com.randps.randomdefence.domain.team.service.port.TeamRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeTeamRepository implements TeamRepository {

    private final List<Team> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public Optional<Team> findByTeamName(String teamName) {
        return data.stream().filter(item -> item.getTeamName().equals(teamName)).findAny();
    }

    @Override
    public Optional<Team> findByTeamNumber(Integer teamNumber) {
        return data.stream().filter(item -> item.getTeamNumber().equals(teamNumber)).findAny();
    }

    @Override
    public Team save(Team team) {
        if (team.getId() == null || team.getId() == 0L) {
            autoIncreasingCount++;
            Team newTeam = Team.builder()
                    .id(autoIncreasingCount)
                    .teamNumber(team.getTeamNumber())
                    .teamName(team.getTeamName())
                    .teamPoint(team.getTeamPoint())
                    .build();
            data.add(newTeam);
            return newTeam;
        } else {
            data.removeIf(item -> item.getId().equals(team.getId()));
            Team newTeam = Team.builder()
                    .id(team.getId())
                    .teamNumber(team.getTeamNumber())
                    .teamName(team.getTeamName())
                    .teamPoint(team.getTeamPoint())
                    .build();
            data.add(newTeam);
            return newTeam;
        }
    }

    @Override
    public List<Team> findAll() {
        return data;
    }
}

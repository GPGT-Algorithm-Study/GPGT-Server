package com.randps.randomdefence.domain.team.service;

import com.randps.randomdefence.domain.team.domain.Team;
import com.randps.randomdefence.domain.team.domain.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    /*
     * 팀의 점수를 올린다.
     */
    public void increaseTeamScore(Integer teamNumber, Integer point) {
        Optional<Team> team = teamRepository.findByTeamNumber(teamNumber);

        // 팀이 없다면 팀 스코어를 올리지 않는다.
        if (!team.isPresent()) return;

        team.get().increasePoint(point);
        teamRepository.save(team.get());
    }
}

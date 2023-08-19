package com.randps.randomdefence.domain.team.service;

import com.randps.randomdefence.domain.team.domain.Team;
import com.randps.randomdefence.domain.team.domain.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    /*
     * 팀의 점수를 올린다.
     */
    public void increaseTeamScore(Integer teamNumber, Integer point) {
        Team team = teamRepository.findByTeamNumber(teamNumber).orElseThrow(() -> new IllegalArgumentException("팀이 설정되지 않았습니다."));

        team.increasePoint(point);
        teamRepository.save(team);
    }
}

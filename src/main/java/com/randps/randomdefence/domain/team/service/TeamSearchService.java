package com.randps.randomdefence.domain.team.service;

import com.randps.randomdefence.domain.team.domain.Team;
import com.randps.randomdefence.domain.team.domain.TeamRepository;
import com.randps.randomdefence.domain.team.dto.TeamInfoResponse;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamSearchService {

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    /*
     * 원하는 팀의 정보를 반환한다.
     */
    public TeamInfoResponse searchTeamInfo(Integer teamNumber) {
        Team team = teamRepository.findByTeamNumber(teamNumber).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        List<User> users = userRepository.findAllByTeam(team.getTeamNumber());

        TeamInfoResponse teamInfoResponse = TeamInfoResponse.builder()
                .name(team.getTeamName())
                .point(team.getTeamPoint())
                .teamList(users)
                .build();

        return teamInfoResponse;
    }
}

package com.randps.randomdefence.domain.statistics.dto;

import com.randps.randomdefence.domain.team.domain.Team;
import com.randps.randomdefence.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class TeamStatisticsDto {

    private Team team;

    private Integer solved;

    private UserTeamStatisticsDto topContributor;

    private List<UserTeamStatisticsDto> users;

    @Builder
    public TeamStatisticsDto(Team team, Integer solved, UserTeamStatisticsDto topContributor, List<UserTeamStatisticsDto> users) {
        this.team = team;
        this.solved = solved;
        this.topContributor = topContributor;
        this.users = users;
    }
}

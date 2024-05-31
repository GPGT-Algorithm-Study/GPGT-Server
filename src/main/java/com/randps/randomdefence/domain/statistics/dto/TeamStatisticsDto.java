package com.randps.randomdefence.domain.statistics.dto;

import com.randps.randomdefence.domain.team.domain.Team;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class TeamStatisticsDto {

    private Team team;

    private Integer rank;

    private Integer score;

    private Integer solved;

    private UserTeamStatisticsDto topContributor;

    private List<UserTeamStatisticsDto> users;

    @Builder
    public TeamStatisticsDto(Team team, Integer rank, Integer score, Integer solved, UserTeamStatisticsDto topContributor, List<UserTeamStatisticsDto> users) {
        this.team = team;
        this.rank = rank;
        this.score = score;
        this.solved = solved;
        this.topContributor = topContributor;
        this.users = users;
    }
}

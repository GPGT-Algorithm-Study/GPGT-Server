package com.randps.randomdefence.domain.team.dto;

import com.randps.randomdefence.domain.user.domain.User;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class TeamInfoResponse {

    private String name;

    private Integer point;

    private List<User> teamList;

    @Builder
    TeamInfoResponse(String name, Integer point, List<User> teamList) {
        this.name = name;
        this.point = point;
        this.teamList = teamList;
    }

}

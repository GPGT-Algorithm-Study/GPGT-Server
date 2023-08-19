package com.randps.randomdefence.domain.team.controller;

import com.randps.randomdefence.domain.team.dto.TeamInfoResponse;
import com.randps.randomdefence.domain.team.service.TeamSearchService;
import com.randps.randomdefence.domain.team.service.TeamSettingService;
import com.randps.randomdefence.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {

    private final TeamSettingService teamSettingService;

    private final TeamSearchService teamSearchService;

    /*
     * 모든 유저의 팀을 반으로 나눠 세팅합니다.
     */
    @GetMapping("/set")
    public List<List<User>> setTeam() {
        return teamSettingService.setUsers();
    }

    /*
     * 팀을 생성한다. (테스트)
     */
    @PostMapping("/make/init")
    public void makeInitialTeam() {
        teamSettingService.makeTeamInitialData();
    }

    /*
     * 특정 팀의 정보와 유저를 반환합니다.
     */
    @GetMapping("/info")
    public TeamInfoResponse findTeamInfo(@Param("teamNumber") Integer teamNumber) {
        return teamSearchService.searchTeamInfo(teamNumber);
    }
}

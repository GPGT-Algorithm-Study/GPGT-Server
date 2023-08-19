package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.domain.UserStatisticsRepository;
import com.randps.randomdefence.domain.statistics.dto.TeamStatisticsDto;
import com.randps.randomdefence.domain.statistics.dto.TeamStatisticsResponse;
import com.randps.randomdefence.domain.statistics.dto.UserTeamStatisticsDto;
import com.randps.randomdefence.domain.team.domain.Team;
import com.randps.randomdefence.domain.team.domain.TeamRepository;
import com.randps.randomdefence.domain.team.service.TeamService;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamStatisticsService {

    private final UserService userService;

    private final UserStatisticsService userStatisticsService;

    private final UserStatisticsRepository userStatisticsRepository;

    private final TeamService teamService;

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    /*
     * 전체 팀 통계를 조회한다.
     */
    public TeamStatisticsResponse findAllTeamStat() {
        // 첫 번째 팀 조회 및 통계 생성
        Team team1 = teamRepository.findByTeamNumber(0).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        List<User> users1 = userRepository.findAllByTeam(0);
        List<UserTeamStatisticsDto> userStatDtos1 = new ArrayList<>(); // 팀에 소속된 팀원들
        UserTeamStatisticsDto topContributor1 = new UserTeamStatisticsDto(); // 한 주간 팀에 가장 많이 기여한 사람
        Integer solved1 = 0; // 한 주간 팀이 푼 전체 문제 수

        // 유저 팀 통계 dto로 변환
        for (User user : users1) {
            UserTeamStatisticsDto dto = UserTeamStatisticsDto.builder()
                    .bojHandle(user.getBojHandle())
                    .notionId(user.getNotionId())
                    .profileImg(user.getProfileImg())
                    .emoji(user.getEmoji())
                    .point(user.getPoint())
                    .build();

            // 팀이 푼 문제 수 더하기
            Optional<UserStatistics> userStatistics = userStatisticsRepository.findByBojHandle(dto.getBojHandle());
            if (userStatistics.isPresent())
                solved1 += userStatistics.get().getWeeklySolvedProblemCount();

            // 가장 기여 많은 유저 찾아서 갱신
            if (topContributor1.getPoint() < dto.getPoint())
                topContributor1 = dto;

            // 추가
            userStatDtos1.add(dto);
        }

        TeamStatisticsDto firstTeam = TeamStatisticsDto.builder()
                .team(team1)
                .solved(solved1)
                .topContributor(topContributor1)
                .users(userStatDtos1)
                .build();

        // 두 번째 팀 조회 및 통계 생성
        Team team2 = teamRepository.findByTeamNumber(1).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        List<User> users2 = userRepository.findAllByTeam(1);
        List<UserTeamStatisticsDto> userStatDtos2 = new ArrayList<>(); // 팀에 소속된 팀원들
        UserTeamStatisticsDto topContributor2 = new UserTeamStatisticsDto(); // 한 주간 팀에 가장 많이 기여한 사람
        Integer solved2 = 0; // 한 주간 팀이 푼 전체 문제 수

        // 유저 팀 통계 dto로 변환
        for (User user : users2) {
            UserTeamStatisticsDto dto = UserTeamStatisticsDto.builder()
                    .bojHandle(user.getBojHandle())
                    .notionId(user.getNotionId())
                    .profileImg(user.getProfileImg())
                    .emoji(user.getEmoji())
                    .point(user.getPoint())
                    .build();

            // 팀이 푼 문제 수 더하기
            Optional<UserStatistics> userStatistics = userStatisticsRepository.findByBojHandle(dto.getBojHandle());
            if (userStatistics.isPresent())
                solved2 += userStatistics.get().getWeeklySolvedProblemCount();

            // 가장 기여 많은 유저 찾아서 갱신
            if (topContributor2.getPoint() < dto.getPoint())
                topContributor2 = dto;

            // 추가
            userStatDtos2.add(dto);
        }


        TeamStatisticsDto secondTeam = TeamStatisticsDto.builder()
                .team(team2)
                .solved(solved2)
                .topContributor(topContributor2)
                .users(userStatDtos2)
                .build();

        // 팀 통계를 모을 리스트
        List<TeamStatisticsDto> teamStatisticsDtos = new ArrayList<>();
        teamStatisticsDtos.add(firstTeam);
        teamStatisticsDtos.add(secondTeam);

        // 팀 통게 합치기
        TeamStatisticsResponse teamStatisticsResponse = TeamStatisticsResponse.builder().teams(teamStatisticsDtos).build();

        return teamStatisticsResponse;
    }
}

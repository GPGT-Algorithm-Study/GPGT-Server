package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.domain.UserStatisticsRepository;
import com.randps.randomdefence.domain.statistics.dto.TeamStatisticsDto;
import com.randps.randomdefence.domain.statistics.dto.TeamStatisticsResponse;
import com.randps.randomdefence.domain.statistics.dto.UserTeamStatisticsDto;
import com.randps.randomdefence.domain.statistics.dto.UserUserStatisticsPairDto;
import com.randps.randomdefence.domain.team.domain.Team;
import com.randps.randomdefence.domain.team.domain.TeamRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamStatisticsService {

    private final UserStatisticsRepository userStatisticsRepository;

    private final TeamRepository teamRepository;

    /**
     * 전체 팀 통계를 조회한다. (Querydsl)
     */
    public TeamStatisticsResponse findAllTeamStat() {
        // 첫 번째 팀 조회 및 통계 생성
        Team team1 = teamRepository.findByTeamNumber(0).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        List<UserUserStatisticsPairDto> users1 = userStatisticsRepository.findAllByTeam(0);
        List<UserTeamStatisticsDto> userStatDtos1 = new ArrayList<>(); // 팀에 소속된 팀원들
        UserTeamStatisticsDto topContributor1 = new UserTeamStatisticsDto(); // 한 주간 팀에 가장 많이 기여한 사람
        int solved1 = 0; // 한 주간 팀이 푼 전체 문제 수
        int score1 = 0; // 팀 포인트

        // 유저 팀 통계 dto로 변환
        for (UserUserStatisticsPairDto user : users1) {
            UserTeamStatisticsDto dto = UserTeamStatisticsDto.builder()
                    .bojHandle(user.getBojHandle())
                    .notionId(user.getNotionId())
                    .profileImg(user.getProfileImg())
                    .emoji(user.getEmoji())
                    .point(0)
                    .build();

            // 팀이 푼 문제 수 더하기, 포인트 더하기
            if (user.getWeeklyEarningPoint() != null && user.getWeeklySolvedProblemCount() != null) {
                solved1 += user.getWeeklySolvedProblemCount();
                score1 += user.getWeeklyEarningPoint();

                // 유저의 포인트 추가
                dto.setPoint(user.getWeeklyEarningPoint());
            }

            // 가장 기여 많은 유저 찾아서 갱신
            if (topContributor1.getPoint() < dto.getPoint())
                topContributor1 = dto;

            // 추가
            userStatDtos1.add(dto);
        }

        TeamStatisticsDto firstTeam = TeamStatisticsDto.builder()
                .team(team1)
                .rank(0)
                .score(score1)
                .solved(solved1)
                .topContributor(topContributor1)
                .users(userStatDtos1)
                .build();

        // 두 번째 팀 조회 및 통계 생성
        Team team2 = teamRepository.findByTeamNumber(1).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        List<UserUserStatisticsPairDto> users2 = userStatisticsRepository.findAllByTeam(1);
        List<UserTeamStatisticsDto> userStatDtos2 = new ArrayList<>(); // 팀에 소속된 팀원들
        UserTeamStatisticsDto topContributor2 = new UserTeamStatisticsDto(); // 한 주간 팀에 가장 많이 기여한 사람
        int solved2 = 0; // 한 주간 팀이 푼 전체 문제 수
        int score2 = 0; // 팀 포인트

        // 유저 팀 통계 dto로 변환
        for (UserUserStatisticsPairDto user : users2) {
            UserTeamStatisticsDto dto = UserTeamStatisticsDto.builder()
                    .bojHandle(user.getBojHandle())
                    .notionId(user.getNotionId())
                    .profileImg(user.getProfileImg())
                    .emoji(user.getEmoji())
                    .point(0)
                    .build();

            // 팀이 푼 문제 수 더하기, 포인트 더하기
            if (user.getWeeklyEarningPoint() != null && user.getWeeklySolvedProblemCount() != null) {
                solved2 += user.getWeeklySolvedProblemCount();
                score2 += user.getWeeklyEarningPoint();

                // 유저의 포인트 추가
                dto.setPoint(user.getWeeklyEarningPoint());
            }

            // 가장 기여 많은 유저 찾아서 갱신
            if (topContributor2.getPoint() < dto.getPoint())
                topContributor2 = dto;

            // 추가
            userStatDtos2.add(dto);
        }

        TeamStatisticsDto secondTeam = TeamStatisticsDto.builder()
                .team(team2)
                .rank(0)
                .score(score2)
                .solved(solved2)
                .topContributor(topContributor2)
                .users(userStatDtos2)
                .build();

        // 등수 정하기
        if (firstTeam.getScore() > secondTeam.getScore()) {
            firstTeam.setRank(1);
            secondTeam.setRank(2);
        } else if (firstTeam.getScore() < secondTeam.getScore()) {
            firstTeam.setRank(2);
            secondTeam.setRank(1);
        } else {
            firstTeam.setRank(1);
            secondTeam.setRank(1);
        }

        // 팀 통계를 모을 리스트
        List<TeamStatisticsDto> teamStatisticsDtos = new ArrayList<>();
        teamStatisticsDtos.add(firstTeam);
        teamStatisticsDtos.add(secondTeam);

        // 팀 통게 합치기
        return TeamStatisticsResponse.builder().teams(teamStatisticsDtos).build();
    }

}

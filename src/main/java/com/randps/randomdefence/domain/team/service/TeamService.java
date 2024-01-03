package com.randps.randomdefence.domain.team.service;

import com.randps.randomdefence.domain.log.service.PointLogSaveService;
import com.randps.randomdefence.domain.team.domain.Team;
import com.randps.randomdefence.domain.team.service.port.TeamRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    private final PointLogSaveService pointLogSaveService;

    /*
     * 팀의 점수를 올린다.
     */
    public void increaseTeamScore(Integer teamNumber, Integer point) {
        // 팀이 할당되지 않았다면 넘어간다.
        if (teamNumber == null) return;

        Optional<Team> team = teamRepository.findByTeamNumber(teamNumber);

        // 팀이 없다면 팀 스코어를 올리지 않는다.
        if (team.isEmpty()) return;

        team.get().increasePoint(point);
        teamRepository.save(team.get());
    }

    /*
     * 팀 결과 주간 결산 포인트 지급
     */
    public void weeklyTeamPointDistribution() {
        Team firstTeam = teamRepository.findByTeamNumber(0).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        Team secondTeam = teamRepository.findByTeamNumber(1).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        List<User> winingTeamUsers;
        String teamName;
        Integer winingPoint;

        // 승리한 팀의 유저들을 뽑는다.
        if (firstTeam.getTeamPoint() > secondTeam.getTeamPoint()) {
            winingTeamUsers = userRepository.findAllByTeam(0);
            teamName = firstTeam.getTeamName();

            // 승리한 팀의 포인트를 유저들이 나눠가진다.
            winingPoint = (5 + (firstTeam.getTeamPoint() / winingTeamUsers.size())) / 3;
        } else {
            winingTeamUsers = userRepository.findAllByTeam(1);
            teamName = secondTeam.getTeamName();

            // 승리한 팀의 포인트를 유저들이 나눠가진다.
            winingPoint = (5 + (secondTeam.getTeamPoint() / winingTeamUsers.size())) / 3;
        }

        // 승리한 팀의 유저들에게 포인트를 지급한다.
        // 팀의 전체 포인트를 나눠가진다.
        for (User user : winingTeamUsers) {
            user.increasePoint(winingPoint);
            userRepository.save(user);

            // 포인트 로그를 기록한다.
            pointLogSaveService.savePointLog(user.getBojHandle(), winingPoint, winingPoint + " points earned by Team [" + teamName + "] Winning!🎉 Congratulation 🥳", true);
        }
    }
}

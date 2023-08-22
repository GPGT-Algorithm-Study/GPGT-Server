package com.randps.randomdefence.domain.team.service;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import com.randps.randomdefence.domain.team.domain.Team;
import com.randps.randomdefence.domain.team.domain.TeamRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class TeamSettingService {

    private final TeamRepository teamRepository;

    private final UserService userService;

    private final UserStatisticsService userStatisticsService;

    private final UserRepository userRepository;

    /*
     * 모든 유저를 0번팀과 1번팀에 반반 할당한다.
     */
    public List<List<User>> setUsers() {
        List<User> users = userRepository.findAll();
        Random r = new Random();
        List<Integer> firstTeamUserIndexes = new ArrayList<>();
        List<Integer> secondTeamUserIndexes = new ArrayList<>();
        // 결과를 반환할 리스트
        List<List<User>> teamUserIndexes = new ArrayList<>();
        teamUserIndexes.add(new ArrayList<User>());
        teamUserIndexes.add(new ArrayList<User>());

        // 두 팀중 한명이 더 많은 팀을 랜덤하게 정한다.
        Integer smallSize = users.size() / 2;
        Integer size;
        if (r.nextInt(2) == 1) {
            size = smallSize;
        } else {
            size = users.size() - smallSize;
        }

        // [0,size)범위의 랜덤한 숫자 2/size개를 뽑는다.
        // 이 숫자에 뽑힌 유저의 인덱스가 0번팀
        while (firstTeamUserIndexes.size() < size) {
            Integer randIdx = r.nextInt(users.size());
            if (!firstTeamUserIndexes.contains(randIdx)) {
                firstTeamUserIndexes.add(randIdx);
                System.out.print(randIdx + ", ");
            }
        }

        // 1번팀의 유저들을 명시적으로 리스트에 넣는다.
        for (Integer i=0;i<users.size();i++) {
            if (firstTeamUserIndexes.contains(i)) continue;
            else secondTeamUserIndexes.add(i);
        }

        // 두 팀을 DB에서 뽑는다.
        Team firstTeam = teamRepository.findByTeamNumber(0).orElseThrow(() -> new IllegalArgumentException("팀이 먼저 DB에 생성되어야 합니다."));
        Team secondTeam = teamRepository.findByTeamNumber(1).orElseThrow(() -> new IllegalArgumentException("팀이 먼저 DB에 생성되어야 합니다."));

        firstTeam.resetTeamPoint();
        secondTeam.resetTeamPoint();

        // 뽑힌 유저를 첫 번째 팀에 할당한다.
        for (Integer i=0;i<firstTeamUserIndexes.size();i++) {
            User user = users.get(firstTeamUserIndexes.get(i));
            user.setTeamNumber(0);
            userRepository.save(user);
            teamUserIndexes.get(0).add(user);

            // 뽑힌 유저의 포인트를 팀 점수에 반영한다.
            UserStatistics stat = userStatisticsService.findByBojHandle(user.getBojHandle());
            firstTeam.increasePoint(stat.getWeeklyEarningPoint());
            teamRepository.save(firstTeam);
        }

        // 뽑히지 않은 유저를 두 번째 팀에 할당한다.
        for (Integer i=0;i<secondTeamUserIndexes.size();i++) {
            User user = users.get(secondTeamUserIndexes.get(i));
            user.setTeamNumber(1);
            userRepository.save(user);
            teamUserIndexes.get(1).add(user);

            // 뽑힌 유저의 포인트를 팀 점수에 반영한다.
            UserStatistics stat = userStatisticsService.findByBojHandle(user.getBojHandle());
            secondTeam.increasePoint(stat.getWeeklyEarningPoint());
            teamRepository.save(secondTeam);
        }

        return teamUserIndexes;
    }

    /*
     * 추가된 유저를 0번팀과 1번팀 중 하나에 할당한다.
     */
    public void setUser(User user) {
        List<User> firstTeamUsers = userRepository.findAllByTeam(0);
        List<User> secondTeamUsers = userRepository.findAllByTeam(1);
        Random r = new Random();
        Integer randTeamIdx;

        if ((firstTeamUsers.isEmpty() && secondTeamUsers.isEmpty()) || firstTeamUsers.size() == secondTeamUsers.size()) {
            // 두 팀중 랜덤한 팀에 배정된다.
            randTeamIdx = r.nextInt(2);
        } else if (secondTeamUsers.isEmpty() || firstTeamUsers.size() > secondTeamUsers.size()) {
            // 0번 팀이 더 많다면 1번 팀으로 배정
            randTeamIdx = 1;
        } else {
            // 1번 팀이 더 많다면 0번 팀으로 배정
            randTeamIdx = 0;
        }

        if (randTeamIdx == 0) {
            Team firstTeam = teamRepository.findByTeamNumber(0).orElseThrow(() -> new IllegalArgumentException("팀이 먼저 DB에 생성되어야 합니다."));

            // 뽑힌 유저를 첫 번째 팀에 할당한다.
            user.setTeamNumber(0);
            userRepository.save(user);

            // 뽑힌 유저의 포인트를 팀 점수에 반영한다.
            UserStatistics stat = userStatisticsService.findByBojHandle(user.getBojHandle());
            firstTeam.increasePoint(stat.getWeeklyEarningPoint());
            teamRepository.save(firstTeam);
        } else {
            Team secondTeam = teamRepository.findByTeamNumber(1).orElseThrow(() -> new IllegalArgumentException("팀이 먼저 DB에 생성되어야 합니다."));

            // 뽑힌 유저를 두 번째 팀에 할당한다.
            user.setTeamNumber(1);
            userRepository.save(user);

            // 뽑힌 유저의 포인트를 팀 점수에 반영한다.
            UserStatistics stat = userStatisticsService.findByBojHandle(user.getBojHandle());
            secondTeam.increasePoint(stat.getWeeklyEarningPoint());
            teamRepository.save(secondTeam);
        }

    }

    /*
     * 팀 주간 초기화
     */
    public void initWeekly() {
        List<Team> teams = teamRepository.findAll();

        for (Team team : teams) {
            team.resetTeamPoint();
            teamRepository.save(team);
        }
    }

    /*
     * 초기 팀 데이터 생성 (테스트)
     */
    public void makeTeamInitialData() {
        List<Team> teams = teamRepository.findAll();

        // 이미 두 개의 팀이 존재한다면 다시 생성하지 않는다.
        if (teams.size() == 2) return ;

        // 팀을 생성 후 저장한다.
        Team firstTeam = Team.builder()
                .teamNumber(0)
                .teamName("햇님")
                .teamPoint(0)
                .build();
        Team secondTeam = Team.builder()
                .teamNumber(1)
                .teamName("달님")
                .teamPoint(0)
                .build();
        teamRepository.save(firstTeam);
        teamRepository.save(secondTeam);
    }
}
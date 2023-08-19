package com.randps.randomdefence.domain.team.service;

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

        // 뽑힌 유저를 첫 번째 팀에 할당한다.
        for (Integer i=0;i<firstTeamUserIndexes.size();i++) {
            User user = users.get(firstTeamUserIndexes.get(i));
            user.setTeamNumber(0);
            userRepository.save(user);
            teamUserIndexes.get(0).add(user);
        }

        // 뽑히지 않은 유저를 두 번째 팀에 할당한다.
        for (Integer i=0;i<secondTeamUserIndexes.size();i++) {
            User user = users.get(secondTeamUserIndexes.get(i));
            user.setTeamNumber(1);
            userRepository.save(user);
            teamUserIndexes.get(1).add(user);
        }

        return teamUserIndexes;
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
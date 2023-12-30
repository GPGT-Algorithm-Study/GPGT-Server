package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import com.randps.randomdefence.domain.statistics.domain.UserProblemStatisticsRepository;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarDto;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarGraphStatisticsResponse;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SolvedBarGraphStatisticsService {

    private final UserRepository userRepository;

    private final UserProblemStatisticsRepository userProblemStatisticsRepository;

    /*
     * 모든 유저의 난이도별 문제 수 바 그래프용 통계를 만들어서 반환한다.
     */
    @Transactional
    public SolvedBarGraphStatisticsResponse getAllSolvedBarStatistics() {
        SolvedBarGraphStatisticsResponse solvedBarGraphStatisticsResponse = new SolvedBarGraphStatisticsResponse();
        List<SolvedBarDto> userBars = new ArrayList<>();

        // 모든 회원의 문제를 채운다.
        List<User> users = userRepository.findAll();
        for (User user : users) {
            // 유저의 문제 난이도 통계를 찾는다.
            Optional<UserProblemStatistics> userProblemStatistics = userProblemStatisticsRepository.findByBojHandle(user.getBojHandle());
            UserProblemStatistics userStat;

            userStat = userProblemStatistics.orElseGet(() -> new UserProblemStatistics(user.getBojHandle()));

            // 유저와 문제 난이도 통계를 조합해서 결과에 추가한다.
            SolvedBarDto solvedBar = SolvedBarDto.builder()
                    .user(user)
                    .userProblemStatistics(userStat)
                    .build();
            userBars.add(solvedBar);
        }

        solvedBarGraphStatisticsResponse.setUserBars(userBars);

        return solvedBarGraphStatisticsResponse;
    }

}

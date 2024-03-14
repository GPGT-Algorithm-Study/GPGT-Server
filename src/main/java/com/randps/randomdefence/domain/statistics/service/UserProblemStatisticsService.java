package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import com.randps.randomdefence.domain.statistics.service.port.UserProblemStatisticsRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class UserProblemStatisticsService {

    private final UserProblemStatisticsRepository userProblemStatisticsRepository;

    /*
     * 유저 난이도별 문제수 통계를 생성한다.
     */
    @Transactional
    public void save(String bojHandle) {
        // 이미 존재한다면 생성하지 않는다.
        Optional<UserProblemStatistics> existUserProblemStatistics = userProblemStatisticsRepository.findByBojHandle(
                bojHandle);
        if (existUserProblemStatistics.isPresent()) {
            return;
        }

        UserProblemStatistics userProblemStatistics = new UserProblemStatistics(bojHandle);

        userProblemStatisticsRepository.save(userProblemStatistics);
    }

    /*
     * 유저 난이도별 문제수 통계를 업데이트 한다.
     */
    @Transactional
    public void updateByDto(String bojHandle, ProblemDto problem) {
        UserProblemStatistics userProblemStatistics = userProblemStatisticsRepository.findByBojHandle(bojHandle)
                .orElseThrow(() -> new IllegalArgumentException("유저의 문제 난이도별 통계를 찾을 수 없습니다."));

        userProblemStatistics.addStat(problem);
        userProblemStatisticsRepository.save(userProblemStatistics);
    }

    /*
     * 유저 난이도별 문제수 통계 조회
     */
    public UserProblemStatistics findByBojHandle(String bojHandle) {
        return userProblemStatisticsRepository.findByBojHandle(bojHandle)
                .orElseThrow(() -> new IllegalArgumentException("유저의 문제 난이도별 통계를 찾을 수 없습니다."));
    }
}

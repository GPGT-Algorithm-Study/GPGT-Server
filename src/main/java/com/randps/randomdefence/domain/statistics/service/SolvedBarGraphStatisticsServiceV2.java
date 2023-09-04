package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import com.randps.randomdefence.domain.statistics.domain.UserProblemStatisticsRepository;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarDto;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarGraphStatisticsResponse;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarPair;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SolvedBarGraphStatisticsServiceV2 {

    private final UserRepository userRepository;

    private final UserProblemStatisticsRepository userProblemStatisticsRepository;

    /**
     * 모든 유저의 난이도별 문제 수 바 그래프용 통계를 만들어서 반환한다. (Querydsl)
     */
    @Transactional
    public List<SolvedBarPair> getAllSolvedBarStatistics() {
        List<SolvedBarPair> userBars = userProblemStatisticsRepository.findAllSolvedBarPair();

        return userBars;
    }


}

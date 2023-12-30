package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.domain.UserProblemStatisticsRepository;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarPair;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SolvedBarGraphStatisticsServiceV2 {

    private final UserProblemStatisticsRepository userProblemStatisticsRepository;

    /**
     * 모든 유저의 난이도별 문제 수 바 그래프용 통계를 만들어서 반환한다. (Querydsl)
     */
    @Transactional
    public List<SolvedBarPair> getAllSolvedBarStatistics() {

        return userProblemStatisticsRepository.findAllSolvedBarPair();
    }


}

package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.domain.UserStatisticsRepository;
import com.randps.randomdefence.domain.statistics.dto.PointBarPair;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointBarGraphStatisticsServiceV2 {

    private final UserStatisticsRepository userStatisticsRepository;

    /**
     * 모든 유저의 포인트 통계를 만들어서 반환한다. (Querydsl)
     */
    @Transactional
    public List<PointBarPair> getAllPointBarStatistics() {

        return userStatisticsRepository.findAllUserAndUserStat();
    }

}

package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.domain.UserProblemStatisticsRepository;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarPair;
import com.randps.randomdefence.domain.statistics.dto.UserWarningBarDto;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WarningBarGraphStatisticsServiceV2 {

    private final UserRepository userRepository;

    /**
     * 모든 유저의 문제 풀었는지 여부를 함께 조회한다. (Querydsl)
     */
    @Transactional
    public List<UserWarningBarDto> getAllWarningBarStatistics() {
        List<UserWarningBarDto> userWarningBars = userRepository.findAllWarningBarDto();

        return userWarningBars;
    }
}

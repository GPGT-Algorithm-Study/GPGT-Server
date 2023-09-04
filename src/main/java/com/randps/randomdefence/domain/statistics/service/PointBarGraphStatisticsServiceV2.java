package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.domain.UserStatisticsRepository;
import com.randps.randomdefence.domain.statistics.dto.PointBarDto;
import com.randps.randomdefence.domain.statistics.dto.PointBarGraphStatisticsResponse;
import com.randps.randomdefence.domain.statistics.dto.PointBarPair;
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
public class PointBarGraphStatisticsServiceV2 {

    private final UserRepository userRepository;

    private final UserStatisticsRepository userStatisticsRepository;

    /*
     * 모든 유저의 포인트 통계를 만들어서 반환한다.
     */
    @Transactional
    public List<PointBarPair> getAllPointBarStatistics() {
        List<PointBarPair> userBars = userStatisticsRepository.findAllUserAndUserStat();

        return userBars;
    }

}

package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.dto.PointBarDto;
import com.randps.randomdefence.domain.statistics.dto.PointBarGraphStatisticsResponse;
import com.randps.randomdefence.domain.statistics.service.port.UserStatisticsRepository;
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
public class PointBarGraphStatisticsService {

    private final UserRepository userRepository;

    private final UserStatisticsRepository userStatisticsRepository;

    /*
     * 모든 유저의 포인트 통계를 만들어서 반환한다.
     */
    @Transactional
    public PointBarGraphStatisticsResponse getAllPointBarStatistics() {
        PointBarGraphStatisticsResponse pointBarGraphStatisticsResponse = new PointBarGraphStatisticsResponse();
        List<PointBarDto> userBars = new ArrayList<>();

        // 모든 회원의 문제를 채운다.
        List<User> users = userRepository.findAll();
        for (User user : users) {
            Optional<UserStatistics> userStat = userStatisticsRepository.findByBojHandle(user.getBojHandle());
            PointBarDto pointBarDto;

            if (userStat.isPresent()) {
                // 회원의 통계가 존재한다면 통계치로 값을 채운다.
                pointBarDto = PointBarDto.builder()
                        .user(user)
                        .dailyEarningPoint(userStat.get().getDailyEarningPoint())
                        .weeklyEarningPoint(userStat.get().getWeeklyEarningPoint())
                        .totalEarningPoint(userStat.get().getTotalEarningPoint())
                        .build();
            } else {
                // 회원의 통계가 존재하지 않는다면 0으로 값을 채운다.
                pointBarDto = PointBarDto.builder()
                        .user(user)
                        .dailyEarningPoint(0)
                        .weeklyEarningPoint(0)
                        .totalEarningPoint(0)
                        .build();
            }

            // 결과 리스트에 추가한다.
            userBars.add(pointBarDto);
        }

        pointBarGraphStatisticsResponse.setUserBars(userBars);

        return pointBarGraphStatisticsResponse;
    }

}

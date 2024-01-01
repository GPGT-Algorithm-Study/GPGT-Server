package com.randps.randomdefence.domain.statistics.service.port;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.dto.PointBarPair;
import com.randps.randomdefence.domain.statistics.dto.UserStatisticsPairDto;
import java.util.List;
import java.util.Optional;

public interface UserStatisticsRepository {

    Optional<UserStatistics> findByBojHandle(String bojHandle);

    UserStatistics save(UserStatistics userStatistics);

    List<UserStatistics> findAll();

    // Team에 따라 Teams 페이지를 만드는데 필요한 데이터 쌍 {User, UserStatistics} 리스트 반환
    List<UserStatisticsPairDto> findAllByTeam(Integer team);

    // 모든 유저에 대해 유저와 유저의 포인트 통계를 함께 조회
    List<PointBarPair> findAllUserAndUserStat();
}

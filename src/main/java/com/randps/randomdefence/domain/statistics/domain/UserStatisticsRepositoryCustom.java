package com.randps.randomdefence.domain.statistics.domain;

import com.randps.randomdefence.domain.statistics.dto.UserUserStatisticsPairDto;

import java.util.List;

public interface UserStatisticsRepositoryCustom {
    // Team에 따라 Teams 페이지를 만드는데 필요한 데이터 쌍 {User, UserStatistics} 리스트 반환
    List<UserUserStatisticsPairDto> findAllByTeam(Integer team);
}

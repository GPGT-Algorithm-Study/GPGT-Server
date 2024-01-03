package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.dto.UserWarningBarDto;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WarningBarGraphStatisticsServiceV2 {

    private final UserRepository userRepository;

    /**
     * 모든 유저의 문제 풀었는지 여부를 함께 조회한다. (Querydsl)
     */
    @Transactional
    public List<UserWarningBarDto> getAllWarningBarStatistics() {

        return userRepository.findAllWarningBarDto();
    }
}

package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.dto.UserIsTodaySolvedDto;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserStatisticsServiceV2 {

    private final UserRepository userRepository;

    /**
     * 모든 유저가 오늘 문제를 풀었는지 여부를 반환한다. (Qeurydsl)
     */
    @Transactional
    public List<UserIsTodaySolvedDto> getAllUserIsTodaySolved() {
        List<UserIsTodaySolvedDto> isTodaySolvedDtos = userRepository.findAllUserIsTodaySolvedDto();

        return isTodaySolvedDtos;
    }
}

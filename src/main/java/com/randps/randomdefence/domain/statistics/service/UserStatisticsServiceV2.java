package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.statistics.dto.UserIsTodaySolvedDto;
import com.randps.randomdefence.domain.statistics.dto.YesterdayUnsolvedUserDto;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserStatisticsServiceV2 {

    private final UserRepository userRepository;

    /**
     * 모든 유저가 오늘 문제를 풀었는지 여부를 반환한다. (Qeurydsl)
     */
    @Transactional
    public List<UserIsTodaySolvedDto> getAllUserIsTodaySolved() {
        return userRepository.findAllUserIsTodaySolvedDto();
    }

    /*
     * 어제 안 푼 유저 목록을 반환한다.
     */
    public List<YesterdayUnsolvedUserDto> getAllYesterdayUnsolvedUser() {
        return userRepository.findAllYesterdayUnsolvedUserDto();
    }
}

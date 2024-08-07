package com.randps.randomdefence.domain.user.service.port;

import com.randps.randomdefence.domain.statistics.dto.UserIsTodaySolvedDto;
import com.randps.randomdefence.domain.statistics.dto.UserWarningBarDto;
import com.randps.randomdefence.domain.statistics.dto.YesterdayUnsolvedUserDto;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import com.randps.randomdefence.domain.user.dto.UserLastLoginLogDto;
import com.randps.randomdefence.domain.user.dto.UserMentionDto;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByBojHandle(String bojHandle);

    List<User> findAllByTeam(Integer team);

    List<User> findAllByManager(Boolean manager);

    List<User> findAll();

    User save(User user);

    void delete(User user);

    // 모든 유저에 대한 UserInfoResponse를 조회해서 반환한다.
    List<UserInfoResponse> findAllUserResponse();

    // 모든 유저의 경고 그래프 DTO를 조회해서 반환한다.
    List<UserWarningBarDto> findAllWarningBarDto();

    // 모든 유저의 오늘 문제 풀었는지 여부 DTO를 조회해서 반환한다.
    List<UserIsTodaySolvedDto> findAllUserIsTodaySolvedDto();

    // 어제 문제를 풀지 않은 모든 유저 DTO를 조회해서 반환한다.
    List<YesterdayUnsolvedUserDto> findAllYesterdayUnsolvedUserDto();

    // 모든 유저의 로그인 기록 DTO를 조회해서 반환한다.
    List<UserLastLoginLogDto> findAllLastLoginDto();

    // mention을 위해 모든 유저의 notionId DTO를 조회해서 반환한다.
    List<UserMentionDto> findAllUserMentionDto();

}

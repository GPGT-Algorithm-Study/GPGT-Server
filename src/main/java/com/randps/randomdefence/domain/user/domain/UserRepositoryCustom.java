package com.randps.randomdefence.domain.user.domain;

import com.randps.randomdefence.domain.statistics.dto.UserIsTodaySolvedDto;
import com.randps.randomdefence.domain.statistics.dto.UserWarningBarDto;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;

import java.util.List;

public interface UserRepositoryCustom {
    // 모든 유저에 대한 UserInfoResponse를 조회해서 반환한다.
    List<UserInfoResponse> findAllUserResponse();

    // 모든 유저의 경고 그래프 DTO를 조회해서 반환한다.
    List<UserWarningBarDto> findAllWarningBarDto();

    // 모든 유저의 오늘 문제 풀었는지 여부 DTO를 조회해서 반환한다.
    List<UserIsTodaySolvedDto> findAllUserIsTodaySolvedDto();
}

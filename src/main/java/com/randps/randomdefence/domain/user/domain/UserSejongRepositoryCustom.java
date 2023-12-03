package com.randps.randomdefence.domain.user.domain;

import com.randps.randomdefence.domain.statistics.dto.UserRankDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserSejongRepositoryCustom {
    /**
     * 세종대학교 유저 전체 랭킹 페이징조회
     */
    Page<UserRankDto> findAllUserRankPaging(Pageable pageable);

}

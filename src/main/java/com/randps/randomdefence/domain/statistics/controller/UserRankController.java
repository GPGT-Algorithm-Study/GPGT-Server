package com.randps.randomdefence.domain.statistics.controller;

import com.randps.randomdefence.domain.statistics.dto.UserRankDto;
import com.randps.randomdefence.domain.statistics.service.UserRankService;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stat/user/rank")
public class UserRankController {

    private final UserRankService userRankService;

    /*
     * 문제 많이 푼 순서대로 유저 랭킹 조회
     */
    @GetMapping("/most-solved")
    public List<UserInfoResponse> getMostSolved() {
        return userRankService.mostSolvedRankedUserInfos();
    }

    /**
     * 문제 많이 푼 순서대로 유저 랭킹 페이징 조회
     */
    @GetMapping("/sejong")
    public Page<UserRankDto> getRankPaging(Pageable pageable) {
        return userRankService.findUserSejongRank(pageable);
    }

}

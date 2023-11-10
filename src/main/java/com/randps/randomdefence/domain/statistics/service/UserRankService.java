package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import com.randps.randomdefence.domain.user.service.UserInfoService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRankService {

    private final UserInfoService userInfoService;

    private final UserRepository userRepository;

    /*
     * 문제 많이 푼 순서대로 유저 랭킹 조회
     */
    @Transactional
    public List<UserInfoResponse> mostSolvedRankedUserInfos() {
        List<UserInfoResponse> userInfos = userInfoService.getAllInfo();

        // 많이 푼 순서대로 정렬
        userInfos.sort((u1, u2) -> u2.getTotalSolved() - u1.getTotalSolved());
        return userInfos;
    }

}

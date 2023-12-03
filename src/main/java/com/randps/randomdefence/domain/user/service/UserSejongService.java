package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.user.domain.UserSejongRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSejongService {

    private final UserSejongRepository userSejongRepository;

    private final UserAlreadySolvedService userAlreadySolvedService;

    /**
     * 세종대 유저가 이미 푼 문제 저장
     */
    @Transactional
    public void saveUserAlreadySolvedProblems() {

    }

    /**
     * 가장 많은 사람이 푼 문제 순으로 문제 반환
     */
    @Transactional
    public void findAllUserBySolvedProblemCountDced() {

    }

}

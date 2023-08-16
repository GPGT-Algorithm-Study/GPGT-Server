package com.randps.randomdefence.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.component.parser.BojParserImpl;
import com.randps.randomdefence.component.parser.SolvedacParserImpl;
import com.randps.randomdefence.user.domain.User;
import com.randps.randomdefence.user.domain.UserRandomStreak;
import com.randps.randomdefence.user.domain.UserRepository;
import com.randps.randomdefence.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserInfoService {
    private final UserRepository userRepository;

    private final UserRandomStreakService userRandomStreakService;

    private final SolvedacParserImpl solvedacParser;

    private final BojParserImpl bojParser;

    /*
     * 유저의 프로필 정보를 불러온다.
     */
    @Transactional
    public UserInfoResponse getInfo(String bojHandle) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        UserRandomStreak userRandomStreak = userRandomStreakService.findUserRandomStreak(bojHandle);

        return user.toUserInfoResponse(userRandomStreak.getMaxRandomStreak());
    }

    /*
     * 모든 유저의 프로필 정보를 불러온다.
     */
    @Transactional
    public List<UserInfoResponse> getAllInfo() {
        List<User> users = userRepository.findAll();
        List<UserInfoResponse> userInfoResponses = new ArrayList<>();

        for (User user : users) {
            UserRandomStreak userRandomStreak = userRandomStreakService.findUserRandomStreak(user.getBojHandle());
            userInfoResponses.add(user.toUserInfoResponse(userRandomStreak.getMaxRandomStreak()));
        }

        return userInfoResponses;
    }


    /*
     * 유저의 프로필 정보를 크롤링 후, DB에 저장한다.
     */
    @Transactional
    public void crawlUserInfo(String bojHandle) throws JsonProcessingException {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        user.setScrapingUserInfo(solvedacParser.getSolvedUserInfo(bojHandle));
        userRepository.save(user);
    }

    /*
     * 모든 유저의 프로필 정보를 크롤링 후, DB에 저장한다.
     */
    @Transactional
    public void crawlUserInfoAll() throws JsonProcessingException {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            user.setScrapingUserInfo(solvedacParser.getSolvedUserInfo(user.getBojHandle()));
            userRepository.save(user);
        }
    }

    /*
     * 모든 유저의 스트릭 끊김 여부를 확인 후, 스트릭이 끊겼다면 경고를 1 올린다. (Daily batch job 서버용)
     */
    @Transactional
    public void checkAllUserSolvedStreak() throws JsonProcessingException {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            user.setScrapingUserInfo(solvedacParser.getSolvedUserInfo(user.getBojHandle()));
            userRepository.save(user);
            if (user.getCurrentStreak().equals(0)) {
                user.increaseWarning();
                userRepository.save(user);
            }
        }
    }

    /*
     * 유저의 프로필 정보를 불러온다. (직접 불러오기)
     */
    @Transactional
    public JsonNode getInfoRaw(String bojHandle) throws JsonProcessingException {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return solvedacParser.crawlingUserInfo(bojHandle);
    }

    /*
     * 유저가 오늘 푼 문제 목록을 불러온다. (직접 불러오기)
     */
    @Transactional
    public List<Object> getTodaySolvedRaw(String bojHandle) throws JsonProcessingException {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return bojParser.getSolvedProblemList(bojHandle);
    }

}

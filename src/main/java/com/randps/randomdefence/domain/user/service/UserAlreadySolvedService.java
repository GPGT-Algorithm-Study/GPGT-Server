package com.randps.randomdefence.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserAlreadySolved;
import com.randps.randomdefence.domain.user.domain.UserAlreadySolvedRepository;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import com.randps.randomdefence.global.component.parser.BojProfileParserImpl;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAlreadySolvedService {

    private final UserAlreadySolvedRepository userAlreadySolvedRepository;

    private final BojProfileParserImpl bojProfileParser;

    private final UserRepository userRepository;

    /*
     * 유저의 이전에 푼 문제 목록을 저장한다.
     */
    public void save(String bojHandle, List<Integer> alreadySolvedList) {
        Optional<UserAlreadySolved> userAlreadySolved = userAlreadySolvedRepository.findByBojHandle(bojHandle);
        UserAlreadySolved target;

        if (userAlreadySolved.isPresent()) {
            // 이미 있다면 문제 리스트만 다시 저장한다.
            target = userAlreadySolved.get();
            target.setAlreadySolvedList(alreadySolvedList);
        } else {
            // 없다면 새로 만들어서 저장한다.
            target = UserAlreadySolved.builder()
                    .bojHandle(bojHandle)
                    .alreadySolvedList(alreadySolvedList)
                    .build();
        }

        userAlreadySolvedRepository.save(target);
    }

    /*
     * 유저가 이전에 푼 문제 목록에 있는지 여부를 반환한다.
     */
    public Boolean isSolved(String bojHandle, Integer problemId) {
        Optional<UserAlreadySolved> userAlreadySolved = userAlreadySolvedRepository.findByBojHandle(bojHandle);
        UserAlreadySolved target;
        List<Integer> userSolvedList;

        // 존재하지 않는다면 false
        if (userAlreadySolved.isEmpty()) {
            return false;
        } else {
            target = userAlreadySolved.get();
        }

        // 존재한다면 리스트에서 찾는다.
        userSolvedList = target.getAlreadySolvedList();

        // 존재하지 않는다면 false
        if (userSolvedList == null || userSolvedList.isEmpty()) {
            return false;
        }

        // 존재한다면 true
        return userSolvedList.contains(problemId);
    }

    /*
     * 특정 유저가 기존에 푼 모든 문제를 스크래핑하고 저장한다.
     */
    public void saveScrapingData(String bojHandle) throws JsonProcessingException {
        List<Integer> solvedList = (List<Integer>) ((List<?>) bojProfileParser.getSolvedProblemList(bojHandle));

        save(bojHandle, solvedList);
    }

    /*
     * 모든 유저가 기존에 푼 모든 문제를 스크래핑하고 저장한다.
     */
    public void saveAllScrapingData() throws JsonProcessingException {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            List<Integer> solvedList = (List<Integer>) ((List<?>) bojProfileParser.getSolvedProblemList(
                    user.getBojHandle()));

            save(user.getBojHandle(), solvedList);
        }
    }

    /*
     * 특정 유저가 기존에 푼 모든 문제를 스크래핑한다. (raw data)
     */
    public List<Object> scrapingDataRaw(String bojHandle) throws JsonProcessingException {
        return bojProfileParser.getSolvedProblemList(bojHandle);
    }
}

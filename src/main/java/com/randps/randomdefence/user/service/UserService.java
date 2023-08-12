package com.randps.randomdefence.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.component.parser.BojParserImpl;
import com.randps.randomdefence.component.parser.SolvedacParserImpl;
import com.randps.randomdefence.component.query.Query;
import com.randps.randomdefence.component.query.SolvedacQueryImpl;
import com.randps.randomdefence.user.domain.User;
import com.randps.randomdefence.user.domain.UserRepository;
import com.randps.randomdefence.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final SolvedacParserImpl solvedacParser;

    private final BojParserImpl bojParser;

    /*
     * 유저를 DB에 저장한다.
     */
    @Transactional
    public User save(String bojHandle, String notionId, Long manager) {
        if (!(manager == 0 || manager == 1)) {
            throw new IllegalArgumentException("잘못된 파라미터가 전달되었습니다.");
        }
        User user = User.builder()
                .bojHandle(bojHandle)
                .notionId(notionId)
                .manager(manager==1?true:false)
                .warning(0)
                .build();

        userRepository.save(user);

        return user;
    }

    /*
     * 유저를 DB에서 삭제한다.
     */
    @Transactional
    public void delete(String bojHandle) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        userRepository.delete(user);
    }

    /*
     * 유저의 정보를 불러온다.
     */
    @Transactional
    public UserInfoResponse getInfo(String bojHandle) throws JsonProcessingException {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        //TODO: 지금은 직접 불러오는데 내부 DB에서 가져오게 수정해야함.
        UserInfoResponse userInfoResponse = solvedacParser.getSolvedUserInfo(bojHandle);
        userInfoResponse.setNotionId(user.getNotionId());
        userInfoResponse.setWarning(user.getWarning());
        userInfoResponse.setIsManager(user.getManager());

        return userInfoResponse;
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

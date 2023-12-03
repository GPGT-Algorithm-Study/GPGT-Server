package com.randps.randomdefence.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.domain.user.domain.UserSejong;
import com.randps.randomdefence.domain.user.domain.UserSejongRepository;
import com.randps.randomdefence.domain.user.dto.UserSejongDto;
import com.randps.randomdefence.global.component.parser.BojProfileParserImpl;
import com.randps.randomdefence.global.component.parser.BojSejongParserImpl;
import com.randps.randomdefence.global.component.parser.SolvedacSejongParserImpl;
import com.randps.randomdefence.global.component.query.Query;
import com.randps.randomdefence.global.component.query.SolvedacProfileImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class UserSejongCrawlingService {

    private final UserSejongRepository userSejongRepository;

    private final BojSejongParserImpl sejongParser;

    private final BojProfileParserImpl bojProfileParser;

    private final UserAlreadySolvedService userAlreadySolvedService;

    private final SolvedacSejongParserImpl solvedacSejongParser;

    /*
     * 세종대학교 페이지의 모든 유저를 크롤링한다.
     */
    @Transactional
    public Integer crawlingAllUserInSejong() throws JsonProcessingException {
        List<Object> users = sejongParser.getAllUserList();

        for (Object user : users) {
            UserSejong userSejong = UserSejong.builder()
                    .bojHandle(user.toString())
                    .profileImg("null")
                    .tier(0)
                    .totalSolved(0)
                    .build();
            userSejongRepository.save(userSejong);
        }

        return users.size();
    }

    /*
     * 모든 세종대학교 유저의 이미 푼 문제를 크롤링한다.
     */
    @Transactional
    public void crawlingAllUserSolvedProblemsInSejong(Integer half) throws JsonProcessingException {
        List<UserSejong> users = userSejongRepository.findAll();
        Integer size = users.size();
        if (half == 0) {
            users = users.subList(0, size/2);
        } else {
            users = users.subList(size/2, size);
        }

        for (UserSejong user : users) {
            List<Integer> solvedList = (List<Integer>) ((List<?>) bojProfileParser.getSolvedProblemList(user.getBojHandle()));

            user.updateTotalSolved(solvedList.size());
            userAlreadySolvedService.save(user.getBojHandle(), solvedList);
        }
    }

    /*
     * 모든 세종대학교 유저의 정보를 저장한다. (api)
     */
    @Transactional
    public Integer saveAllUserInfoInSejong(Integer idx, Integer total) throws JsonProcessingException {
        List<UserSejong> users = userSejongRepository.findAll();
        Integer size = users.size();

        if (size < size/total * (idx + 1))
            users = users.subList(size/total * idx, size);
        else
            users = users.subList(size/total * idx, size/total * (idx + 1));

        for (UserSejong user : users) {
            saveSolvedacUserSejongInfo(user);
        }

        return size/total;
    }

    /*
     * 한 세종대학교 유저의 정보를 요청하고 저장한다. (api)
     */
    @Transactional
    public void saveSolvedacUserSejongInfo(UserSejong user) {
        Query query = new SolvedacProfileImpl();

        query.setDomain("https://solved.ac/api/v3/user/show");

        query.setParam("handle", user.getBojHandle());

        RestTemplate restTemplate = new RestTemplate();

        System.out.println(query.getQuery());
        ResponseEntity<JsonNode> response;
        try {
            response = restTemplate.getForEntity(query.getQuery(), JsonNode.class);
            JsonNode userInfo = response.getBody();

            user.update(user.getBojHandle(), userInfo.path("profileImageUrl").asText(), userInfo.path("tier").asInt(), userInfo.path("solvedCount").asInt());

            userSejongRepository.save(user);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw new IllegalStateException(e.getMessage());
            }
        }
    }

    /*
     * solvedac 랭킹페이지의 세종대 학생들의 정보를 저장한다.
     */
    @Transactional
    public Integer crawlingAllSejongUserInfo() throws JsonProcessingException {
        List<UserSejongDto> userSejongDtos = solvedacSejongParser.crawlingUserInfos();
        List<UserSejong> users = userSejongRepository.findAll();
        List<UserSejong> modifiedUsers = new ArrayList<>();

        for (UserSejongDto userDto : userSejongDtos) {
            String bojHandle = userDto.getBojHandle();

            // users에서 bojHandle이 일치하는 사용자 찾기
            Optional<UserSejong> optionalUser = users.stream()
                    .filter(user -> bojHandle.equals(user.getBojHandle()))
                    .findFirst();

            if (optionalUser.isPresent()) {
                // 사용자를 찾았을 경우 업데이트
                UserSejong existingUser = optionalUser.get();
                existingUser.update(userDto);

                modifiedUsers.add(existingUser);
            }
        }
        // 업데이트된 사용자 저장
        userSejongRepository.saveAll(modifiedUsers);

        return modifiedUsers.size();
    }
}

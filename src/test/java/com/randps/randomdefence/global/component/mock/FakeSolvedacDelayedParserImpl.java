package com.randps.randomdefence.global.component.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.global.component.parser.SolvedacParser;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.time.LocalDateTime;
import java.util.List;

public class FakeSolvedacDelayedParserImpl  implements SolvedacParser {

    private final UserScrapingInfoDto userScrapingInfoDto;

    private LocalDateTime startOfActiveDay;

    public FakeSolvedacDelayedParserImpl(UserScrapingInfoDto userScrapingInfoDto) {
        this.userScrapingInfoDto = userScrapingInfoDto;
    }

    @Override
    public List<Object> getSolvedProblemList(String userName) throws JsonProcessingException {
        return null;
    }

    @Override
    public JsonNode crawlingUserInfo(String bojHandle) throws JsonProcessingException {
        return null;
    }

    @Override
    public Boolean isTodaySolved(JsonNode userInfo) {
        return null;
    }

    @Override
    public Integer countTodaySolved(JsonNode userInfo) {
        return null;
    }

    @Override
    public UserScrapingInfoDto getSolvedUserInfo(String bojHandle) throws JsonProcessingException {
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            throw new RuntimeException("error occurred while waiting for 10 seconds.");
        }
        return userScrapingInfoDto;
    }

    @Override
    public void setStartOfActiveDay(LocalDateTime startOfActiveDay) {
        this.startOfActiveDay = startOfActiveDay;
    }
}
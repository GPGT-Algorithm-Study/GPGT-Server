package com.randps.randomdefence.global.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;

public interface SolvedacParser extends Parser {

    JsonNode crawlingUserInfo(String bojHandle) throws JsonProcessingException;

    Boolean isTodaySolved(JsonNode userInfo);

    Integer countTodaySolved(JsonNode userInfo);

    UserScrapingInfoDto getSolvedUserInfo(String bojHandle) throws JsonProcessingException;

}

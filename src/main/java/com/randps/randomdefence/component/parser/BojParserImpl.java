package com.randps.randomdefence.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.randps.randomdefence.component.crawler.WebCrawler;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BojParserImpl implements Parser {
    private JsonNode userSolvedList;

    private final WebCrawler webCrawler;

    /*
     * 오늘 푼 문제 리스트를 반환한다.
     */
    @Override
    public List<Object> getSolvedProblemList(String bojHandle) throws JsonProcessingException {
        List<Object> solvedProblems = new ArrayList<>();
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https").host("www.acmicpc.net").path("/status")
                .queryParam("problem_id", "")
                .queryParam("user_id", bojHandle)
                .queryParam("language_id", "-1")
                .queryParam("result_id", 4)
                .build();

        webCrawler.setUrl(uri.toUriString());
        solvedProblems = webCrawler.process("baekjoon");

        return solvedProblems;
    }

    public static String convertDifficulty(Integer difficulty) {
        if (difficulty == 0) {return "Unrated";}
        if (difficulty == 1) {return "브론즈 5";}
        if (difficulty == 2) {return "브론즈 4";}
        if (difficulty == 3) {return "브론즈 3";}
        if (difficulty == 4) {return "브론즈 2";}
        if (difficulty == 5) {return "브론즈 1";}
        if (difficulty == 6) {return "실버 5";}
        if (difficulty == 7) {return "실버 4";}
        if (difficulty == 8) {return "실버 3";}
        if (difficulty == 9) {return "실버 2";}
        if (difficulty == 10) {return "실버 1";}
        if (difficulty == 11) {return "골드 5";}
        if (difficulty == 12) {return "골드 4";}
        if (difficulty == 13) {return "골드 3";}
        if (difficulty == 14) {return "골드 2";}
        if (difficulty == 15) {return "골드 1";}
        if (difficulty == 16) {return "플레티넘 5";}
        if (difficulty == 17) {return "플레티넘 4";}
        if (difficulty == 18) {return "플레티넘 3";}
        if (difficulty == 19) {return "플레티넘 2";}
        if (difficulty == 20) {return "플레티넘 1";}
        if (difficulty == 21) {return "다이아몬드 5";}
        if (difficulty == 22) {return "다이아몬드 4";}
        if (difficulty == 23) {return "다이아몬드 3";}
        if (difficulty == 24) {return "다이아몬드 2";}
        if (difficulty == 25) {return "다이아몬드 1";}
        if (difficulty == 26) {return "루비 5";}
        if (difficulty == 27) {return "루비 4";}
        if (difficulty == 28) {return "루비 3";}
        if (difficulty == 29) {return "루비 2";}
        if (difficulty == 30) {return "루비 1";}

        return "Unknown";
    }
}

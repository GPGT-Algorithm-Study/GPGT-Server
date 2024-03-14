package com.randps.randomdefence.global.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.global.component.crawler.BojWebCrawler;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
@Qualifier("bojParserToUse")
public class BojParserImpl implements Parser {

    private LocalDateTime startOfActiveDay;

    private final BojWebCrawler webCrawler;

    /*
     * 오늘 푼 문제 리스트를 반환한다.
     */
    @Override
    public List<Object> getSolvedProblemList(String bojHandle) throws JsonProcessingException {
        List<Object> solvedProblems;
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https").host("www.acmicpc.net").path("/status")
                .queryParam("problem_id", "")
                .queryParam("user_id", bojHandle)
                .queryParam("language_id", "-1")
                .queryParam("result_id", 4)
                .build();

        webCrawler.setUrl(uri.toUriString());
        webCrawler.setStartOfActiveDay(startOfActiveDay);
        solvedProblems = webCrawler.process();

        System.out.println(
            "[LOG] bojHandle = " + bojHandle + ", today solved problem count = "
                + solvedProblems.size());

        return solvedProblems;
    }

    @Override
    public void setStartOfActiveDay(LocalDateTime startOfActiveDay) {
        this.startOfActiveDay = startOfActiveDay;
    }

}

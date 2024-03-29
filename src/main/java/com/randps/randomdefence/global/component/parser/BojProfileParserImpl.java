package com.randps.randomdefence.global.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.global.component.crawler.BojProfileWebCrawler;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
@Qualifier("bojProfileParserToUse")
public class BojProfileParserImpl implements Parser {

    private LocalDateTime startOfActiveDay;

    private final BojProfileWebCrawler bojProfileWebCrawler;

    /*
     * 기존에 푼 문제 리스트를 반환한다.
     */
    @Override
    public List<Object> getSolvedProblemList(String bojHandle) throws JsonProcessingException {
        List<Object> solvedProblems = new ArrayList<>();
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https").host("www.acmicpc.net").path("/user").path("/" + bojHandle)
                .build();

        bojProfileWebCrawler.setUrl(uri.toUriString());
        solvedProblems = bojProfileWebCrawler.process();

        return solvedProblems;
    }

    @Override
    public void setStartOfActiveDay(LocalDateTime startOfActiveDay) {
        this.startOfActiveDay = startOfActiveDay;
    }

}

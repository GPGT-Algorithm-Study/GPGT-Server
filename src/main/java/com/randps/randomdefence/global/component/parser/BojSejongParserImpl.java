package com.randps.randomdefence.global.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.global.component.crawler.BojSejongWebCrawler;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class BojSejongParserImpl implements SejongParser {

    private final BojSejongWebCrawler bojSejongWebCrawler;

    /*
     * 기존에 푼 문제 리스트를 반환한다.
     */
    @Override
    public List<Object> getAllUserList() throws JsonProcessingException {
        List<Object> users = new ArrayList<>();
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https").host("www.acmicpc.net").path("/school/ranklist/313")
                .build();

        bojSejongWebCrawler.setUrl(uri.toUriString());
        users.addAll(bojSejongWebCrawler.process());
        for (int i = 1; i <= 11; i++) {
            uri = UriComponentsBuilder.newInstance()
                    .scheme("https").host("www.acmicpc.net").path("/school/ranklist/313")
                    .path("/" + i)
                    .build();

            bojSejongWebCrawler.setUrl(uri.toUriString());
            users.addAll(bojSejongWebCrawler.process());
        }

        return users;
    }

}

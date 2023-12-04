package com.randps.randomdefence.global.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.global.component.crawler.BojProfileSchoolWebCrawler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class BojProfileSchoolParserImpl implements BojProfileParser {

    private final BojProfileSchoolWebCrawler webCrawler;

    @Override
    public List<Object> getBojUserProfile(String bojHandle) throws JsonProcessingException {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https").host("www.acmicpc.net").path("/user").path("/" + bojHandle)
                .build();

        webCrawler.setUrl(uri.toUriString());
        return webCrawler.process();
    }

}

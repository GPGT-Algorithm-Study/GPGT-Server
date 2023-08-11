package com.randps.randomdefence.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.randps.randomdefence.component.crawler.WebCrawler;
import com.randps.randomdefence.component.query.Query;
import com.randps.randomdefence.component.query.SolvedacQueryImpl;
import com.randps.randomdefence.recommendation.dto.RecommendationResponse;
import com.randps.randomdefence.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Component
public class SolvedacParserImpl implements Parser {
    private JsonNode userInfoJson;
    private String profileImg = "";
    private Integer currentStreak;
    private Integer maxStreak;

    private final WebCrawler webCrawler;

    @Override
    public List<Object> getSolvedProblemList(String userName) {
        return null;
    }

    public JsonNode crawlingUserInfo(String bojHandle) throws JsonProcessingException {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https").host("solved.ac").path("/profile/" + bojHandle).build();

        webCrawler.setUrl(uri.toUriString());
        List<Object> elements = webCrawler.process();
        String jsonString = ((Element)elements.get(0)).unwrap().toString();

        ObjectMapper om = new ObjectMapper();
        userInfoJson = om.readTree(jsonString);

        return userInfoJson;
    }

    public Long getSolvedStreak(String bojHandle) throws JsonProcessingException {


        return 0L;
    }

}

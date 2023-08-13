package com.randps.randomdefence.recommendation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.component.parser.BojParserImpl;
import com.randps.randomdefence.component.query.Query;
import com.randps.randomdefence.component.query.SolvedacQueryImpl;
import com.randps.randomdefence.recommendation.dto.RecommendationResponse;
import com.randps.randomdefence.recommendation.dto.TitleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static com.randps.randomdefence.component.parser.BojParserImpl.convertDifficulty;

@RequiredArgsConstructor
@Service
public class RecommendationService {

    /*
     * solvedac의 문제를 랜덤하게 추천받는 내부 쿼리를 만든다.
     */
    @Transactional
    public String makeQuery(String userId, String start, String end) {
        Query query = new SolvedacQueryImpl();

        query.setDomain("https://solved.ac/api/v3/search/problem");

        query.setParam("query", new SolvedacQueryImpl().makeSolvedQuery(userId, start, end));

        // solved에서 random으로 1page의 문제를 오름차순으로 가져오게 한다.
        query.setParam("direction", "asc");
        query.setParam("page", "1");
        query.setParam("sort", "random");

        return query.getQuery();
    }

    /*
     * 추천 문제를 뽑는다.
     */
    @Transactional
    public RecommendationResponse makeRecommend(String url) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode recommendationProblem = response.getBody().path("items").path(0);

        RecommendationResponse recommendationResponse = RecommendationResponse.builder()
                .problemId(recommendationProblem.path("problemId").asInt())
                .titleKo(recommendationProblem.path("titleKo").asText())
                .titles(makeSubJsonTitle(recommendationProblem.path("titles")))
                .isSolvable(recommendationProblem.path("isSolvable").asBoolean())
                .isPartial(recommendationProblem.path("isPartial").asBoolean())
                .acceptedUserCount(recommendationProblem.path("acceptedUserCount").asInt())
                .level(recommendationProblem.path("level").asInt())
                .votedUserCount(recommendationProblem.path("votedUserCount").asInt())
                .sprout(recommendationProblem.path("sprout").asBoolean())
                .givesNoRating(recommendationProblem.path("givesNoRating").asBoolean())
                .isLevelLocked(recommendationProblem.path("isLevelLocked").asBoolean())
                .averageTries(recommendationProblem.path("averageTries").asText())
                .official(recommendationProblem.path("official").asBoolean())
                .tags(makeSubJsonTag(recommendationProblem.path("tags")))
                .build();

        return recommendationResponse;
    }

    /*
     * Json의 title부분을 파싱한다.
     */
    public Object makeSubJsonTitle(JsonNode jsonNode) {
        if (jsonNode.isArray()) {
            ArrayList<Object> subTitles = new ArrayList<Object>();
            int size = jsonNode.size();

            for (int i = 0; i < size; i++) {
                subTitles.add(makeSubJsonTitle(jsonNode.path(i)));
            }
            return subTitles;
        }
        Object subTitle = TitleDto.builder()
                .language(jsonNode.path("language").asText())
                .languageDisplayName(jsonNode.path("languageDisplayName").asText())
                .title(jsonNode.path("title").asText())
                .isOriginal(jsonNode.path("isOriginal").asBoolean())
                .build();

        return subTitle;
    }

    /*
     * Json의 Tag부분을 파싱한다.
     */
    public ArrayList<Object> makeSubJsonTag(JsonNode jsonNode) {
        ArrayList<Object> subTags = new ArrayList<Object>();
        int size = jsonNode.size();

        for (int i = 0; i < size; i++) {
            subTags.add(jsonNode.path(i).path("displayNames").path(0).path("name"));
        }
        return subTags;
    }

}

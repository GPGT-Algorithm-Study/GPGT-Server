package com.randps.randomdefence.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.randps.randomdefence.component.crawler.WebCrawler;
import com.randps.randomdefence.component.query.Query;
import com.randps.randomdefence.component.query.SolvedacQueryImpl;
import com.randps.randomdefence.recommendation.dto.RecommendationResponse;
import com.randps.randomdefence.user.domain.User;
import com.randps.randomdefence.user.dto.UserInfoResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static com.randps.randomdefence.component.parser.BojParserImpl.convertDifficulty;

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
        List<Object> elements = webCrawler.process("solvedac");
        String jsonString = ((Element)elements.get(0)).unwrap().toString();

        ObjectMapper om = new ObjectMapper();
        userInfoJson = om.readTree(jsonString);

        return userInfoJson;
    }

    public Boolean isTodaySolved(JsonNode userInfo) {
        JsonNode grass = userInfo.path("props").path("pageProps").path("grass").path("grass");
        LocalDate now = LocalDate.now();

        for (JsonNode day : grass) {
            if (day.path("date").asText().equals(now.toString()))
                return true;
        }
        return false;
    }

    public UserInfoResponse getSolvedUserInfo(String bojHandle) throws JsonProcessingException {
        // TODO: 내부DB먼저 스캔해서 사용하게 바꿔야함. 일단 개발 단계에서는 바로 스크래핑 데이터 사용
        JsonNode userInfo = crawlingUserInfo(bojHandle);

        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .bojHandle(bojHandle)
                .userTier(convertDifficulty(userInfo.path("props").path("pageProps").path("user").path("tier").asInt()))
                .notionId("")
                .profileImg(userInfo.path("props").path("pageProps").path("user").path("profileImageUrl").asText())
                .currentStreak(userInfo.path("props").path("pageProps").path("grass").path("currentStreak").asInt())
                .warning(0)
                .isManager(false)
                .isTodaySolved(isTodaySolved(userInfo))
                .build();

        return userInfoResponse;
    }

}

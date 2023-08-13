package com.randps.randomdefence.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.randps.randomdefence.component.crawler.SolvedacWebCrawler;
import com.randps.randomdefence.component.parser.dto.UserScrapingInfoDto;
import com.randps.randomdefence.user.dto.UserInfoResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Component
public class SolvedacParserImpl implements Parser {
    private JsonNode userInfoJson;
    private String profileImg = "";
    private Integer currentStreak;
    private Integer maxStreak;

    private final SolvedacWebCrawler webCrawler;

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

    public Boolean isTodaySolved(JsonNode userInfo) {
        JsonNode grass = userInfo.path("props").path("pageProps").path("grass").path("grass");
        LocalDate now = LocalDate.now();

        for (JsonNode day : grass) {
            if (day.path("date").asText().equals(now.toString()))
                return true;
        }
        return false;
    }

    public UserScrapingInfoDto getSolvedUserInfo(String bojHandle) throws JsonProcessingException {
        JsonNode userInfo = crawlingUserInfo(bojHandle);

        UserScrapingInfoDto userscrapingInfoDto = UserScrapingInfoDto.builder()
                .tier(userInfo.path("props").path("pageProps").path("user").path("tier").asInt())
                .profileImg(userInfo.path("props").path("pageProps").path("user").path("profileImageUrl").asText())
                .currentStreak(userInfo.path("props").path("pageProps").path("grass").path("currentStreak").asInt())
                .totalSolved(userInfo.path("props").path("pageProps").path("user").path("solvedCount").asInt())
                .isTodaySolved(isTodaySolved(userInfo))
                .build();

        return userscrapingInfoDto;
    }

}

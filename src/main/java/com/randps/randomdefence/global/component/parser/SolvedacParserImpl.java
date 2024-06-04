package com.randps.randomdefence.global.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.randps.randomdefence.domain.user.domain.UserSetting;
import com.randps.randomdefence.domain.user.service.UserSettingSearchService;
import com.randps.randomdefence.global.component.crawler.SolvedacWebCrawler;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Getter
@RequiredArgsConstructor
@Component
public class SolvedacParserImpl implements SolvedacParser {
    private JsonNode userInfoJson;
    private String profileImg = "";
    private Integer currentStreak;
    private Integer maxStreak;

    private LocalDateTime startOfActiveDay;

    private final SolvedacWebCrawler webCrawler;

    private final UserSettingSearchService userSettingSearchService;

    @Override
    public List<Object> getSolvedProblemList(String userName) {
        return null;
    }

    @Override
    public void setStartOfActiveDay(LocalDateTime startOfActiveDay) {
        this.startOfActiveDay = startOfActiveDay;
    }

    @Override
    public JsonNode crawlingUserInfo(String bojHandle) throws JsonProcessingException {
        UserSetting setting = userSettingSearchService.findByBojHandleSafe(bojHandle);
        if (!setting.getScrapingOn()) {
            log.info("Scraping is off for user: {}", bojHandle);
            return null;
        }

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https").host("solved.ac").path("/profile/" + bojHandle).build();

        webCrawler.setUrl(uri.toUriString());
        List<Object> elements = webCrawler.process();
        if (elements.isEmpty()) {
            return null;
        }
        String jsonString = ((Element)elements.get(0)).unwrap().toString();

        ObjectMapper om = new ObjectMapper();
        userInfoJson = om.readTree(jsonString);

        return userInfoJson;
    }

    @Override
    public Boolean isTodaySolved(JsonNode userInfo) {
        JsonNode grass = userInfo.path("props").path("pageProps").path("grass").path("grass");
        LocalDateTime cur = LocalDateTime.now();
        LocalDate now = LocalDate.now();
        if (cur.getHour() < 6) {
            now = now.minusDays(1);
        }

        for (JsonNode day : grass) {
            if (day.path("date").asText().equals(now.toString()))
                return true;
        }
        return false;
    }

    @Override
    public Integer countTodaySolved(JsonNode userInfo) {
        JsonNode grass = userInfo.path("props").path("pageProps").path("grass").path("grass");
        LocalDateTime cur = LocalDateTime.now();
        LocalDate now = LocalDate.now();
        if (cur.getHour() < 6) {
            now = now.minusDays(1);
        }

        for (JsonNode day : grass) {
            if (day.path("date").asText().equals(now.toString())) {
                return day.path("value").asInt();
            }
        }
        return 0;
    }

    @Override
    public UserScrapingInfoDto getSolvedUserInfo(String bojHandle) throws JsonProcessingException {
        JsonNode userInfo = crawlingUserInfo(bojHandle);

        if (userInfo == null) {
            return null;
        }

        UserScrapingInfoDto userscrapingInfoDto = UserScrapingInfoDto.builder()
                .tier(userInfo.path("props").path("pageProps").path("user").path("tier").asInt())
                .profileImg(userInfo.path("props").path("pageProps").path("user").path("profileImageUrl").asText())
//                .currentStreak(userInfo.path("props").path("pageProps").path("grass").path("currentStreak").asInt())
                .currentStreak(0)
                .totalSolved(userInfo.path("props").path("pageProps").path("user").path("solvedCount").asInt())
                .todaySolvedProblemCount(countTodaySolved(userInfo))
                .isTodaySolved(isTodaySolved(userInfo))
                .build();

        return userscrapingInfoDto;
    }

}

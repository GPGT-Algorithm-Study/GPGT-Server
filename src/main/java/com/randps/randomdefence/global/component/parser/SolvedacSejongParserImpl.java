package com.randps.randomdefence.global.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.randps.randomdefence.domain.user.dto.UserSejongDto;
import com.randps.randomdefence.global.component.crawler.SolvedacWebCrawler;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@RequiredArgsConstructor
@Component
@Qualifier("solvedacSejongParserToUse")
public class SolvedacSejongParserImpl implements Parser {
    private List<UserSejongDto> users = new ArrayList<>();

    private final SolvedacWebCrawler webCrawler;

    @Override
    public List<Object> getSolvedProblemList(String userName) {
        return null;
    }

    public List<UserSejongDto> crawlingUserInfos() throws JsonProcessingException {
        // page 1 to 13
        for (int i=1;i<=13;i++) {
            UriComponents uri = UriComponentsBuilder.newInstance()
                    .scheme("https").host("solved.ac").path("/ranking/o/313?page=" + String.valueOf(i)).build();
            //https://solved.ac/ranking/o/313?page=1

            webCrawler.setUrl(uri.toUriString());
            List<Object> elements = webCrawler.process();
            String jsonString = ((Element) elements.get(0)).unwrap().toString();

            ObjectMapper om = new ObjectMapper();
            users.addAll(findSejongUsersInJson(om.readTree(jsonString)));
        }

        return users;
    }

    public List<UserSejongDto> findSejongUsersInJson(JsonNode userInfoJson) {
        List<UserSejongDto> userSejongDtos = new ArrayList<>();

        userInfoJson = userInfoJson.path("props").path("pageProps").path("rankings").path("items");
        for (JsonNode node : userInfoJson) {
            userSejongDtos.add(UserSejongDto.builder()
                    .bojHandle(String.valueOf(node.path("handle")).replaceAll("\"", "")) // 겹따옴표 제거
                    .profileImg(String.valueOf(node.path("profileImageUrl")).replaceAll("\"", "")) // 겹따옴표 제거
                    .tier(Integer.parseInt(node.path("tier").toString()))
                    .totalSolved(Integer.parseInt(node.path("solvedCount").toString()))
                    .build());
        }

        return userSejongDtos;
    }

}

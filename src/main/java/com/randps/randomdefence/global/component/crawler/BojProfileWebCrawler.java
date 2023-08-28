package com.randps.randomdefence.global.component.crawler;

import com.randps.randomdefence.global.component.crawler.dto.BojProblemPair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.randps.randomdefence.global.component.crawler.BojWebCrawler.is6AmAfter;

@Component
public class BojProfileWebCrawler extends WebCrawler  {

    @Override
    public List<Object> getDataList(Document document) {
        List<Object> list = new ArrayList<>();
        Elements selects = document.select(".panel-body");	//⭐⭐⭐ HTML의 table의 tbody의 tr태그의 값을 가져온다.
        //select 메서드 안에 css selector를 작성하여 Elements를 가져올 수 있다.

        Elements totalSolvedSelect = selects.get(1).select("div div a");
        for (Element select : totalSolvedSelect) {
            list.add(Integer.valueOf(select.text()));

            //html(), text(), children(), append().... 등 다양한 메서드 사용 가능
            //https://jsoup.org/apidocs/org/jsoup/nodes/Element.html 참고
        }

        return list.stream().distinct().collect(Collectors.toList());
    }

}

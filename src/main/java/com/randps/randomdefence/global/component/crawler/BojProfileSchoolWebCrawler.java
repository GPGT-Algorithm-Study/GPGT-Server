package com.randps.randomdefence.global.component.crawler;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class BojProfileSchoolWebCrawler extends WebCrawler {

    @Override
    public List<Object> getDataList(Document document) {
        List<Object> list = new ArrayList<>();
        Elements selects = document.select("table#statics tbody");	//⭐⭐⭐ HTML의 table의 tbody의 tr태그의 값을 가져온다.
        //select 메서드 안에 css selector를 작성하여 Elements를 가져올 수 있다.
        Integer idx = selects.size();
        selects = selects.get(idx - 1).select("td a");

        for (Element select : selects) {
            list.add(select.text());
            System.out.println(select.text());
        }

        return list;
    }

}

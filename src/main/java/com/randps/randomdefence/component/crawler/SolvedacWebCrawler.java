package com.randps.randomdefence.component.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SolvedacWebCrawler extends WebCrawler {

    @Override
    public List<Object> getDataList(Document document) {
        List<Object> list = new ArrayList<>();
        Elements selects = document.select("script#__NEXT_DATA__");	//⭐⭐⭐ HTML의 script태그의 값을 가져온다.
        //select 메서드 안에 css selector를 작성하여 Elements를 가져올 수 있다.

        for (Element select : selects) {
            list.add(select);
            //html(), text(), children(), append().... 등 다양한 메서드 사용 가능
            //https://jsoup.org/apidocs/org/jsoup/nodes/Element.html 참고
        }

        return list;
    }

}

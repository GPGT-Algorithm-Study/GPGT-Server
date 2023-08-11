package com.randps.randomdefence.component.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebCrawler {
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Object> process() {
        Connection conn = Jsoup.connect(url);
        //Jsoup 커넥션 생성

        Document document = null;
        try {
            document = conn.get();
            //url의 내용을 HTML Document 객체로 가져온다.
            //https://jsoup.org/apidocs/org/jsoup/nodes/Document.html 참고
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getDataList(document);
    }

    private List<Object> getDataList(Document document) {
        List<Object> list = new ArrayList<>();
        Elements selects = document.select("script#__NEXT_DATA__");	//⭐⭐⭐ HTML의 script태그의 값을 가져온다.
        //select 메서드 안에 css selector를 작성하여 Elements를 가져올 수 있다.

        for (Element select : selects) {
            list.add(select);
            //System.out.println(select.html());		//⭐⭐⭐
            //html(), text(), children(), append().... 등 다양한 메서드 사용 가능
            //https://jsoup.org/apidocs/org/jsoup/nodes/Element.html 참고
        }

        return list;
    }
}

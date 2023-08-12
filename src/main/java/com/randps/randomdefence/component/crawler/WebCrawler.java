package com.randps.randomdefence.component.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebCrawler {
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Object> process(String platform) {
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

        //TODO: 다형성 가지게 변경할 것
        if (platform.equals("solvedac"))
            return getDataListSolvedac(document);
        if (platform.equals("baekjoon"))
            return getDataListBoj(document);
        return new ArrayList<>();
    }

    private List<Object> getDataListSolvedac(Document document) {
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

    private List<Object> getDataListBoj(Document document) {
        List<Object> list = new ArrayList<>();
        Elements selects = document.select("table#status-table tbody tr");	//⭐⭐⭐ HTML의 table의 tbody의 tr태그의 값을 가져온다.
        //select 메서드 안에 css selector를 작성하여 Elements를 가져올 수 있다.

        for (Element select : selects) {
            // 푼 시간이랑 문제 번호만 뽑아내기
            Elements innerElems = select.select("a");
            if (isToday(innerElems.get(2).attr("title"))) {
                list.add(innerElems.get(1).text());
            }
            //html(), text(), children(), append().... 등 다양한 메서드 사용 가능
            //https://jsoup.org/apidocs/org/jsoup/nodes/Element.html 참고
        }
        
        return list.stream().distinct().collect(Collectors.toList());
    }

    // 태그 안의 값과 비교해서 Today인 경우 넣기
    private Boolean isToday(String DateTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDateTime;
        if (is6AmAfter(now.getHour()))
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        else {
            now = now.minusDays(1);
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        }
        //TODO 새벽 6시부터만 오늘로 판별
        LocalDateTime target = LocalDateTime.of(Integer.valueOf(DateTime.substring(0,4)), Integer.valueOf(DateTime.substring(5,7)), Integer.valueOf(DateTime.substring(8,10)), Integer.valueOf(DateTime.substring(11,13)), Integer.valueOf(DateTime.substring(14,16)), Integer.valueOf(DateTime.substring(18)), 0);

        if (startOfDateTime.isBefore(target)) return true;
        else return false;
    }

    private Boolean is6AmAfter(Integer h) {
        if (h >= 6) return true;
        return false;
    }
}

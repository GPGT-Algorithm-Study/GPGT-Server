package com.randps.randomdefence.component.crawler;

import com.randps.randomdefence.component.crawler.dto.BojProblemPair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BojWebCrawler extends WebCrawler {

    @Override
    public List<Object> getDataList(Document document) {
        List<Object> list = new ArrayList<>();
        Elements selects = document.select("table#status-table tbody tr");	//⭐⭐⭐ HTML의 table의 tbody의 tr태그의 값을 가져온다.
        //select 메서드 안에 css selector를 작성하여 Elements를 가져올 수 있다.

        for (Element select : selects) {
            // 푼 시간이랑 문제 번호만 뽑아내기
            Elements innerElems = select.select("a");
            if (isToday(innerElems.get(2).attr("title"))) {
                BojProblemPair pair = BojProblemPair.builder()
                        .problemId(Integer.valueOf(innerElems.get(1).text()))
                        .title(innerElems.get(1).attr("title"))
                        .dateTime(innerElems.get(2).attr("title"))
                        .build();
                list.add(pair);
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

    public static Boolean is6AmAfter(Integer h) {
        if (h >= 6) return true;
        return false;
    }
}

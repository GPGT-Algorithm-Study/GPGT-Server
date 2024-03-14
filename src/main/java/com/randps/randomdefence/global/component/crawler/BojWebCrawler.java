package com.randps.randomdefence.global.component.crawler;

import com.randps.randomdefence.global.component.crawler.dto.BojProblemPair;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class BojWebCrawler extends WebCrawler {

    private LocalDateTime startOfActiveDay;

    private LocalDateTime endOfActiveDay;

    @Override
    public List<Object> getDataList(Document document) {
        List<Object> list = new ArrayList<>();
        Elements selects = document.select("table#status-table tbody tr");	//⭐⭐⭐ HTML의 table의 tbody의 tr태그의 값을 가져온다.
        //select 메서드 안에 css selector를 작성하여 Elements를 가져올 수 있다.

        Integer cnt = 0;
        for (Element select : selects) {
            // 비어있는 문제 지나가기
            if (select.select("td").get(2).text().isBlank()) {
                BojProblemPair pair = BojProblemPair.builder()
                        .problemId(0)
                        .title("아레나 문제")
                        .dateTime(select.select("a").get(0).attr("title"))
                        .build();
                list.add(pair);
                continue;
            }
            // 푼 시간이랑 문제 번호만 뽑아내기
            Elements innerElems = select.select("a");
            if (isToday(innerElems.get(2).attr("title"))) {
                BojProblemPair pair = BojProblemPair.builder()
                        .problemId(Integer.valueOf(innerElems.get(1).text()))
                        .title(innerElems.get(1).attr("title"))
                        .dateTime(innerElems.get(2).attr("title"))
                        .language(select.select("td").get(6).text())
                        .build();
                list.add(pair);
                cnt++;
            }
            //html(), text(), children(), append().... 등 다양한 메서드 사용 가능
            //https://jsoup.org/apidocs/org/jsoup/nodes/Element.html 참고
        }

        // 다음 페이지로 넘어간다면 중복이라면 재귀적으로 다음 페이지를 파싱한다.
        if (cnt == 20) {
            Element link = document.select("a#next_page").first();	//⭐⭐⭐ HTML의 id가 'next_page'인 태그의 a태그를 선택한다.
            //select 메서드 안에 css selector를 작성하여 Elements를 가져올 수 있다.
            String url = link.attr("href"); // 다음페이지 url
            setUrl(url);
            list.addAll(process());
        }

        return list.stream().distinct().collect(Collectors.toList());
    }

    // 태그 안의 값과 비교해서 Today인 경우 넣기
    private Boolean isToday(String DateTime) {
        //TODO 새벽 6시부터만 오늘로 판별
        LocalDateTime target = LocalDateTime.of(Integer.parseInt(DateTime.substring(0, 4)),
            Integer.parseInt(DateTime.substring(5, 7)), Integer.parseInt(DateTime.substring(8, 10)),
            Integer.parseInt(DateTime.substring(11, 13)),
            Integer.parseInt(DateTime.substring(14, 16)), Integer.parseInt(DateTime.substring(17)),
            0);

        if (startOfActiveDay.isBefore(target) && endOfActiveDay.isAfter(target)) {
            return true;
        } else {
            return false;
        }
    }

    public void setStartOfActiveDay(LocalDateTime startOfActiveDay) {
        this.startOfActiveDay = startOfActiveDay;
        this.endOfActiveDay = startOfActiveDay.plusDays(1);
    }

    public static Boolean is6AmAfter(Integer h) {
        if (h >= 6) return true;
        return false;
    }
}

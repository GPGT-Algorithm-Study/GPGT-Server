package com.randps.randomdefence.global.component.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public abstract class WebCrawler {
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Object> process() {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        Connection conn = Jsoup.connect(url)
                .header("Content-Type", "text/html; charset=utf-8")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:127.0) Gecko/20100101 Firefox/127.0")
                .referrer("https://solved.ac")
                .header("host", "solved.ac")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3")
                .method(Connection.Method.GET)
                .ignoreContentType(true);
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
    abstract protected List<Object> getDataList(Document document);

}

package com.randps.randomdefence.global.component.crawler;

import java.io.IOException;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public abstract class WebCrawler {
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Object> process() {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        Connection conn = Jsoup.connect(url)
                .header("Content-Type","application/x-www-form-urlencoded")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36")
                .referrer("https://solved.ac")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "en-US,en;q=0.9,ko-KR;q=0.8,ko;q=0.7,ja-JP;q=0.6,ja;q=0.5,ru-RU;q=0.4,ru;q=0.3")
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

package com.randps.randomdefence.domain.image.service;

import com.randps.randomdefence.global.component.imageParser.ImageParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageParser imageParser;

    @Transactional
    public void toSave(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        String[] divs = urlPath.split("/");
        File file = new File("/Users/seongmin/testResources/boj/" + divs[divs.length-1]);

        imageParser.saveImage(file, url);
    }

}

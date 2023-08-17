package com.randps.randomdefence.global.component.imageParser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public interface ImageParser {
    void saveImage(File toSave, URL url) throws IOException;
}

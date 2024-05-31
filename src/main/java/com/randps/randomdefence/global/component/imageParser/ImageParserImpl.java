package com.randps.randomdefence.global.component.imageParser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import org.springframework.stereotype.Component;

@Component
public class ImageParserImpl implements ImageParser {

    @Override
    public void saveImage(File toSave, URL url) throws IOException {
        System.out.println("0");
        BufferedImage imageBuffer = ImageIO.read(url);System.out.println("1");

        Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpg");System.out.println("2");
        ImageWriter jpegImageWriter = (ImageWriter) writers.next();System.out.println("3");
        JPEGImageWriteParam writeParam = (JPEGImageWriteParam) jpegImageWriter.getDefaultWriteParam();System.out.println("4");
        writeParam.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);System.out.println("5");

        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(new FileOutputStream(toSave));System.out.println("6");
        jpegImageWriter.setOutput(imageOutputStream);System.out.println("7");
        jpegImageWriter.write(null, new IIOImage(imageBuffer, null, null), writeParam);System.out.println("8");
        jpegImageWriter.dispose();System.out.println("9");
        imageOutputStream.close();System.out.println("10");
    }

}

package com.randps.randomdefence.domain.image.controller;

import com.randps.randomdefence.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("")
    public String urlToImage(@Param("urlPath") String urlPath) {
        try {
            imageService.toSave(urlPath);
        } catch (IOException e) {
            return "bad";
        }
        return "굿";
    }
}

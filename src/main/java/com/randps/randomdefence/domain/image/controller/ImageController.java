package com.randps.randomdefence.domain.image.controller;

import com.randps.randomdefence.domain.image.domain.Image;
import com.randps.randomdefence.domain.image.service.ImageService;
import com.randps.randomdefence.global.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    /*
     * 게시글 ID로 모든 이미지 조회
     */

    /*
     * 모든 이미지 조회
     */
    @GetMapping("/all")
    public List<Image> findAll() {
        return imageService.findAllImage();
    }


}

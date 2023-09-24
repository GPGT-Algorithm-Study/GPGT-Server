package com.randps.randomdefence.domain.image.controller;

import com.randps.randomdefence.domain.image.domain.Image;
import com.randps.randomdefence.domain.image.dto.ImageUploadResponse;
import com.randps.randomdefence.domain.image.service.ImageService;
import com.randps.randomdefence.global.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/image/s3")
public class ImageS3Controller {

    private final ImageService imageService;

    private final S3Service s3Service;

    /*
     * S3 이미지 업로드
     */
    @PostMapping("/upload")
    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // 이미지의 이름을 고유한 UUID로 설정
        String url = s3Service.saveFile(file);

        return ResponseEntity.ok()
                .body(ImageUploadResponse.builder().url(url).build());
    }

    /*
     * S3 이미지 다운로드
     */
    @GetMapping("/download")
    public ResponseEntity<UrlResource> downloadImage(@RequestParam("originalFilename") String originalFilename) {
        UrlResource urlResource = s3Service.downloadImage(originalFilename);
        Image image = imageService.findByOriginalName(originalFilename);

        String contentDisposition = "attachment; filename=\"" +  originalFilename + "." + image.getContentType() + "\"";

        // header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }

    /*
     * S3 이미지 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteImage(@RequestParam("originalFilename") String originalFilename) {
        s3Service.deleteImage(originalFilename);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "이미지를 성공적으로 삭제했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}

package com.randps.randomdefence.global.aws.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.randps.randomdefence.domain.image.domain.Image;
import com.randps.randomdefence.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3;

    private final ImageService imageService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);

        // 서버에 이미지 저장
        imageService.save(amazonS3.getUrl(bucket, originalFilename).toString(), originalFilename, multipartFile.getContentType());

        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    public UrlResource downloadImage(String originalFilename) {
        UrlResource urlResource = new UrlResource(amazonS3.getUrl(bucket, originalFilename));

        return urlResource;
    }

    public void deleteImage(String originalFilename)  {

        // 서버에서 이미지 삭제
        imageService.delete(originalFilename);

        amazonS3.deleteObject(bucket, originalFilename);
    }

    /*
     * S3포함해서 이미지 아이디 리스트로 전부 삭제
     */
    @Transactional
    public void deleteAllByIdListIncludeS3(List<Long> imageIds) {
        List<Image> images = imageService.findAllByIdList(imageIds);

        for (Image image : images) {
            deleteImage(image.getOriginName());
        }
    }
}
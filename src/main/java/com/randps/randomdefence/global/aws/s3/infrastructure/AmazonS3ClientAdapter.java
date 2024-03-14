package com.randps.randomdefence.global.aws.s3.infrastructure;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.randps.randomdefence.global.aws.s3.service.port.AmazonS3ClientPort;
import java.io.InputStream;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AmazonS3ClientAdapter implements AmazonS3ClientPort {

    private final AmazonS3Client amazonS3;

    @Override
    public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata)
            throws SdkClientException, AmazonServiceException {
        return amazonS3.putObject(bucketName, key, input, metadata);
    }

    @Override
    public URL getUrl(String bucketName, String key) {
        return amazonS3.getUrl(bucketName, key);
    }

    @Override
    public void deleteObject(String bucketName, String key) throws SdkClientException, AmazonServiceException {
        amazonS3.deleteObject(bucketName, key);
    }

}

package com.randps.randomdefence.global.mock;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.randps.randomdefence.global.aws.s3.service.port.AmazonS3ClientPort;
import java.io.InputStream;
import java.net.URL;

public class FakeAmazonS3Client implements AmazonS3ClientPort {


    @Override
    public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata)
            throws SdkClientException, AmazonServiceException {
        return null;
    }

    @Override
    public URL getUrl(String bucketName, String key) {
        return null;
    }

    @Override
    public void deleteObject(String bucketName, String key) throws SdkClientException, AmazonServiceException {

    }
}

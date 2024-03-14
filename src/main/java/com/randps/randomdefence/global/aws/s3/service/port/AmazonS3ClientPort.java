package com.randps.randomdefence.global.aws.s3.service.port;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import java.io.InputStream;
import java.net.URL;

public interface AmazonS3ClientPort {

    PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata)
            throws SdkClientException, AmazonServiceException;

    URL getUrl(String bucketName, String key);

    void deleteObject(String bucketName, String key) throws SdkClientException, AmazonServiceException;

}

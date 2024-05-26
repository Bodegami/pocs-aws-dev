package com.bodegami.cadastro.gateway;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;

public interface S3BucketGateway {
    void send(Object object, String name);

    List<String> listFilesFrom(String bucket);

    S3ObjectSummary getFileDetailsFrom(String bucket, String fileName);

    void getFileObject(String bucket, String fileName);
}

package com.bodegami.cadastro.gateway.client;

import com.amazonaws.services.s3.AmazonS3;

public interface AwsS3Client {

    AmazonS3 s3Client();
    
}

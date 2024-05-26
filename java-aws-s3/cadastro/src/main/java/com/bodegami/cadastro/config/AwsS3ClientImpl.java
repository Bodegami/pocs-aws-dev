package com.bodegami.cadastro.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.bodegami.cadastro.gateway.client.AwsS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AwsS3ClientImpl implements AwsS3Client {

    @Value("${aws.s3.endpoint}")
    private String ENDPOINT_URL;

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(getCredentials()))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(ENDPOINT_URL, "us-east-1"))
                .build();
    }

    private AWSCredentials getCredentials() {
        return new BasicAWSCredentials("lambda-localstack", "localstack");
    }

}

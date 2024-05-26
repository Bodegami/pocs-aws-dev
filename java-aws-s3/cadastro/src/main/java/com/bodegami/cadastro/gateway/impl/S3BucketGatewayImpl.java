package com.bodegami.cadastro.gateway.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.*;
import com.bodegami.cadastro.gateway.S3BucketGateway;
import com.bodegami.cadastro.gateway.client.AwsS3Client;
import com.bodegami.cadastro.utils.Helper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class S3BucketGatewayImpl implements S3BucketGateway {

    private final AwsS3Client client;
    private final Helper helper;

    public S3BucketGatewayImpl(AwsS3Client client, Helper helper) {
        this.client = client;
        this.helper = helper;
    }

    @Override
    public void send(Object object, String name) {

        try {

            String fileName = helper.toFileName(name);
            File file = helper.toFile(object, fileName);

            client.s3Client().putObject("my-first-bucket", fileName, file);
            file.delete();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<String> listFilesFrom(String bucket) {
        ObjectListing objectListing = client.s3Client().listObjects(bucket);

        return objectListing.getObjectSummaries().stream().map(S3ObjectSummary::getKey).toList();
    }

    @Override
    public S3ObjectSummary getFileDetailsFrom(String bucket, String fileName) {

        try {

            ObjectListing objectListing = client.s3Client().listObjects(bucket);

            Optional<S3ObjectSummary> result = objectListing.getObjectSummaries().stream()
                    .filter(obj -> obj.getKey().toLowerCase().equalsIgnoreCase(fileName))
                    .findFirst();

            return result.orElseThrow(() -> new IllegalArgumentException("File not found!"));
        }
        catch (AmazonServiceException e) {
            throw new IllegalArgumentException(e.getErrorMessage());
        }
    }

    @Override
    public void getFileObject(String bucket, String fileName) {

        if (fileName.isEmpty() || bucket.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameters!");
        }

        try {
            S3Object o = client.s3Client().getObject(bucket, fileName);
            S3ObjectInputStream s3is = o.getObjectContent();
            FileOutputStream fos = new FileOutputStream(new File(fileName));
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Done!");

    }





    public void listBuckets() {
        List<Bucket> buckets = client.s3Client().listBuckets();

        // Display the bucket names
        System.out.println("Buckets:");
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }


}

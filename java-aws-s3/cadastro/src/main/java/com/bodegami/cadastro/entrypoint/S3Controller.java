package com.bodegami.cadastro.entrypoint;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.bodegami.cadastro.gateway.S3BucketGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3BucketGateway s3Gateway;

    public S3Controller(S3BucketGateway s3Gateway) {
        this.s3Gateway = s3Gateway;
    }

    @PostMapping("/file")
    public ResponseEntity<?> insertFile(@RequestBody Map<String, Object> request,
                                        @RequestHeader(value = "fileName", defaultValue = "request") String fileName) {

        s3Gateway.send(request, fileName);
        System.out.println(request);

        return ResponseEntity.ok(request);
    }

    @GetMapping("/{bucket}/files")
    public ResponseEntity<List<String>> listFiles(@PathVariable("bucket") String bucket) {

        List<String> resultList = s3Gateway.listFilesFrom(bucket);
        System.out.println(resultList);

        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/file/details")
    public ResponseEntity<S3ObjectSummary> getFileDetails(
            @RequestHeader(name = "bucket", defaultValue = "my-first-bucket") String bucket,
            @RequestHeader(name = "fileName") String fileName) {

        System.out.println(fileName);

        S3ObjectSummary result = s3Gateway.getFileDetailsFrom(bucket, fileName);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{bucket}/{fileName}")
    public ResponseEntity<Void> getFileDetailss(@PathVariable("bucket") String bucket, @PathVariable("fileName") String fileName) {

        s3Gateway.getFileObject(bucket, fileName);

        return ResponseEntity.ok().build();
    }

}

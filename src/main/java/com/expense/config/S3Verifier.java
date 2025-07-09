package com.expense.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;

@Component
@ConditionalOnProperty(
    name = "aws.verifyStartUp",
    havingValue = "true",
    matchIfMissing = false
)
public class S3Verifier {

    private final S3Client s3Client;

    public S3Verifier(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @PostConstruct
    public void verify() {
        int bucketCount = s3Client.listBuckets().buckets().size();
        System.out.println("AWS S3 client initialized. Found buckets: " + bucketCount);
    }
}

package com.expense.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class SesService {

    private final SesClient sesClient;

    @Value("${aws.ses.sender}")
    private String sender;

    @Value("${aws.ses.recipient}")
    private String recipient;

    public SesService(@Value("${aws.accessKeyId}") String accessKeyId,
                      @Value("${aws.secretAccessKey}") String secretAccessKey,
                      @Value("${aws.region}") String region) {
        this.sesClient = SesClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .build();
    }

    public void sendExpenseAlertEmail(String description, double amount) {
        String subjectText = "🚨 High Expense Alert: $" + amount;
        String bodyText = "A new expense of $" + amount + " was recorded: " + description;

        Destination destination = Destination.builder()
                .toAddresses(recipient)
                .build();

        Content subjectContent = Content.builder()
                .data(subjectText)
                .charset("UTF-8")
                .build();

        Content bodyContent = Content.builder()
                .data(bodyText)
                .charset("UTF-8")
                .build();

        Body msgBody = Body.builder()
                .text(bodyContent)
                .build();

        Message message = Message.builder()
                .subject(subjectContent)
                .body(msgBody)
                .build();

        SendEmailRequest request = SendEmailRequest.builder()
                .source(sender)
                .destination(destination)
                .message(message)
                .build();

        sesClient.sendEmail(request);
        System.out.println("✅ SES Alert Email Sent");
    }
}

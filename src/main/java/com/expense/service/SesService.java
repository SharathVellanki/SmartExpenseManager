package com.expense.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

@Service
public class SesService {

    private final SesClient sesClient;

    @Value("${aws.ses.sender}")
    private String sender;

    @Value("${aws.ses.recipient}")
    private String recipient;

    public SesService(
        @Value("${aws.accessKeyId:}") String accessKeyId,
        @Value("${aws.secretAccessKey:}") String secretAccessKey,
        @Value("${aws.region:us-east-2}") String region
    ) {
        AwsCredentialsProvider credsProvider;
        if (!accessKeyId.isBlank() && !secretAccessKey.isBlank()) {
            // Use static creds if both variables are provided
            credsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKeyId, secretAccessKey)
            );
        } else {
            // Otherwise fall back to the EC2 instance role or default provider chain
            credsProvider = DefaultCredentialsProvider.create();
        }

        this.sesClient = SesClient.builder()
            .credentialsProvider(credsProvider)
            .region(Region.of(region))
            .build();
    }

    public void sendExpenseAlertEmail(String description, double amount) {
        String subjectText = "🚨 High Expense Alert: $" + amount;
        String bodyText    = "A new expense of $" + amount + " was recorded: " + description;

        Destination destination = Destination.builder()
            .toAddresses(recipient)
            .build();

        Content subject = Content.builder()
            .data(subjectText)
            .charset("UTF-8")
            .build();

        Content body = Content.builder()
            .data(bodyText)
            .charset("UTF-8")
            .build();

        Message message = Message.builder()
            .subject(subject)
            .body(Body.builder().text(body).build())
            .build();

        SendEmailRequest req = SendEmailRequest.builder()
            .source(sender)
            .destination(destination)
            .message(message)
            .build();

        sesClient.sendEmail(req);
        System.out.println(" SES Alert Email Sent");
    }
}

package com.expense.service;

import com.expense.entity.Expense;
import com.expense.entity.User;
import com.expense.payload.ExpenseSummary;
import com.expense.repository.ExpenseRepository;
import com.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final S3Client s3Client;
    private final SesService sesService;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              UserRepository userRepository,
                              S3Client s3Client,
                              SesService sesService) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.s3Client = s3Client;
        this.sesService = sesService;
    }

    @Override
    public Expense addExpense(Expense expense, String username) {
        System.out.println(">>> Starting addExpense for user: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        expense.setUser(user);

        System.out.println(">>> Incoming expense details:");
        System.out.println("    Amount: " + expense.getAmount());
        System.out.println("    Description: " + expense.getDescription());

        Expense saved = expenseRepository.save(expense);
        System.out.println(">>> Expense saved with ID: " + saved.getId());

        if (saved.getAmount() > 1000) {
            System.out.println(">>> High value expense detected. Sending SES alert...");
            try {
                sesService.sendExpenseAlertEmail(saved.getDescription(), saved.getAmount());
                System.out.println("SES alert email sent successfully.");
            } catch (Exception e) {
                System.out.println("SES email failed: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return saved;
    }

    @Override
    public List<Expense> getExpensesByUser(String username) {
        return expenseRepository.findByUserUsername(username);
    }

    @Override
    public List<Expense> getExpensesByUserBetween(String username, LocalDate from, LocalDate to) {
        return expenseRepository.findByUserUsernameAndCreatedAtBetween(
                username,
                from.atStartOfDay(),
                to.plusDays(1).atStartOfDay()
        );
    }

    @Override
    public ExpenseSummary getSummaryByUser(String username) {
        List<Expense> all = expenseRepository.findByUserUsername(username);
        long count = all.size();
        double total = all.stream().mapToDouble(Expense::getAmount).sum();
        return new ExpenseSummary(count, total);
    }

    @Override
    public Expense uploadReceipt(Long expenseId, MultipartFile file) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        String key = "receipts/" + expenseId + "-" + file.getOriginalFilename();

        try {
            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(req, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload receipt to S3", e);
        }

        String url = s3Client.utilities()
                .getUrl(b -> b.bucket(bucketName).key(key))
                .toExternalForm();

        expense.setReceiptUrl(url);
        return expenseRepository.save(expense);
    }
}

package com.expense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartExpenseManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartExpenseManagerApplication.class, args);
        System.out.println(" SmartExpenseManager started successfully");
    }
}

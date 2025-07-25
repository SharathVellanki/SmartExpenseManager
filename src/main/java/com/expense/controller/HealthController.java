package com.expense.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String healthCheck() {
        System.out.println("✅ /health endpoint hit — service is UP");
        return "OK";
    }
}

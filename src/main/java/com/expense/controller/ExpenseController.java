package com.expense.controller;

import com.expense.entity.Expense;
import com.expense.payload.ExpenseSummary;
import com.expense.service.ExpenseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense, Authentication authentication) {
        String username = authentication.getName();
        return expenseService.addExpense(expense, username);
    }

    @GetMapping
    public List<Expense> getExpenses(
        Authentication authentication,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        String username = authentication.getName();
        if (from != null && to != null) {
            return expenseService.getExpensesByUserBetween(username, from, to);
        }
        return expenseService.getExpensesByUser(username);
    }

    @GetMapping("/summary")
    public ExpenseSummary getSummary(Authentication authentication) {
        String username = authentication.getName();
        return expenseService.getSummaryByUser(username);
    }
}

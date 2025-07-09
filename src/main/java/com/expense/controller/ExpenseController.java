
package com.expense.controller;

import com.expense.entity.Expense;
import com.expense.payload.ExpenseSummary;
import com.expense.service.ExpenseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense, Authentication auth) {
        return expenseService.addExpense(expense, auth.getName());
    }

    @GetMapping
    public List<Expense> getExpenses(
        Authentication auth,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        String user = auth.getName();
        if (from != null && to != null) {
            return expenseService.getExpensesByUserBetween(user, from, to);
        }
        return expenseService.getExpensesByUser(user);
    }

    @GetMapping("/summary")
    public ExpenseSummary getSummary(Authentication auth) {
        return expenseService.getSummaryByUser(auth.getName());
    }

    @PostMapping("/{id}/receipt")
    public Expense uploadReceipt(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file,
        Authentication auth
    ) {
        return expenseService.uploadReceipt(id, file);
    }
}

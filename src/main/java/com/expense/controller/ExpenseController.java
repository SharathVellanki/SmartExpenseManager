package com.expense.controller;

import com.expense.entity.Expense;
import com.expense.payload.ExpenseSummary;
import com.expense.service.ExpenseService;
import com.expense.service.SesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final SesService sesService;

    public ExpenseController(ExpenseService expenseService,
                             SesService sesService) {
        this.expenseService = expenseService;
        this.sesService = sesService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addExpense(@RequestBody Expense expense,
                                                          Authentication auth) {
        String username = auth.getName();
        Expense saved = expenseService.addExpense(expense, username);

        // recalc total
        List<Expense> all = expenseService.getExpensesByUser(username);
        double total = all.stream().mapToDouble(Expense::getAmount).sum();

        boolean alertTriggered = false;
        if (total > 1000) {
            sesService.sendExpenseAlertEmail(saved.getDescription(), total);
            alertTriggered = true;
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Expense added");
        resp.put("alertTriggered", alertTriggered);
        return ResponseEntity.ok(resp);
    }

    @GetMapping
    public List<Expense> getExpenses(Authentication auth,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                     LocalDate from,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                     LocalDate to) {
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
    public Expense uploadReceipt(@PathVariable Long id,
                                 @RequestParam("file") MultipartFile file,
                                 Authentication auth) {
        return expenseService.uploadReceipt(id, file);
    }
}

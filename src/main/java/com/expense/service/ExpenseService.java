// src/main/java/com/expense/service/ExpenseService.java
package com.expense.service;

import com.expense.entity.Expense;
import com.expense.payload.ExpenseSummary;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    Expense addExpense(Expense expense, String username);
    List<Expense> getExpensesByUser(String username);
    List<Expense> getExpensesByUserBetween(String username, LocalDate from, LocalDate to);
    ExpenseSummary getSummaryByUser(String username);
    Expense uploadReceipt(Long expenseId, MultipartFile file);
}

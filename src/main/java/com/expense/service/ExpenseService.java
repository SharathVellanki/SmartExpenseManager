package com.expense.service;

import com.expense.entity.Expense;
import com.expense.payload.ExpenseSummary;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    // existing
    Expense addExpense(Expense expense, String username);
    List<Expense> getExpensesByUser(String username);

    // new: get expenses in a date range
    List<Expense> getExpensesByUserBetween(String username, LocalDate from, LocalDate to);

    // new: summarize all expenses
    ExpenseSummary getSummaryByUser(String username);
}

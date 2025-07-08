package com.expense.service;

import com.expense.entity.Expense;
import com.expense.entity.User;
import com.expense.payload.ExpenseSummary;
import com.expense.repository.ExpenseRepository;
import com.expense.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Expense addExpense(Expense expense, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        expense.setUser(user);

        // Simulate email alert if expense is above threshold
        if (expense.getAmount() > 1000) {
            System.out.println("📧 Alert: Expense exceeds $1000. Email notification triggered.");
        }

        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getExpensesByUser(String username) {
        return expenseRepository.findByUserUsername(username);
    }

    @Override
    public List<Expense> getExpensesByUserBetween(String username, LocalDate from, LocalDate to) {
        // Convert LocalDate to LocalDateTime to cover the entire 'to' date
        return expenseRepository.findByUserUsernameAndCreatedAtBetween(
            username,
            from.atStartOfDay(),
            to.plusDays(1).atStartOfDay()
        );
    }

    @Override
    public ExpenseSummary getSummaryByUser(String username) {
        List<Expense> allExpenses = expenseRepository.findByUserUsername(username);
        long count = allExpenses.size();
        double total = allExpenses.stream()
                                  .mapToDouble(Expense::getAmount)
                                  .sum();
        return new ExpenseSummary(count, total);
    }
}

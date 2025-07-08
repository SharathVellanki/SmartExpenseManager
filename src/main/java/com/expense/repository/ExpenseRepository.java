package com.expense.repository;

import com.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // existing method
    List<Expense> findByUserUsername(String username);

    // new: find expenses for a user between two timestamps
    List<Expense> findByUserUsernameAndCreatedAtBetween(
        String username,
        LocalDateTime from,
        LocalDateTime to
    );
}

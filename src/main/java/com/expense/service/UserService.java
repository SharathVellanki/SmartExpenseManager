package com.expense.service;

import com.expense.entity.User;

public interface UserService {
    User register(User user);
    User getByUsername(String username);
}

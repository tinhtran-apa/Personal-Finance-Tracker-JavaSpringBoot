package com.apa.finance_tracker.services;

import com.apa.finance_tracker.entitys.User;

public interface AuthService {
    User registerUser(User user);
    void loginUser(User user);
}

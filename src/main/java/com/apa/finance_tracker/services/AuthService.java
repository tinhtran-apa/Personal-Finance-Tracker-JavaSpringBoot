package com.apa.finance_tracker.services;

import com.apa.finance_tracker.dtos.responses.LoginResponse;
import com.apa.finance_tracker.entitys.User;

public interface AuthService {
    public User register(User user);
    LoginResponse login(String email, String password);
}

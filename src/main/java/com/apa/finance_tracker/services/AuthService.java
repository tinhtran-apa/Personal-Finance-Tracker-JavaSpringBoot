package com.apa.finance_tracker.services;

import com.apa.finance_tracker.entitys.Token;
import com.apa.finance_tracker.entitys.User;

public interface AuthService {
    User registerUser(User user);
    Token loginUser(User user);
}

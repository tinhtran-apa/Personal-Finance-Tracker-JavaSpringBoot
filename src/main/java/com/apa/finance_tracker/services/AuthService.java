package com.apa.finance_tracker.services;

import com.apa.finance_tracker.entitys.Token;
import com.apa.finance_tracker.entitys.User;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.core.Authentication;

import java.text.ParseException;
import java.util.Optional;

public interface AuthService {
    User registerUser(User user);
    Token loginUser(User user);
    Token refreshToken (String refreshToken)  throws ParseException, JOSEException;
    User getMe ();
}

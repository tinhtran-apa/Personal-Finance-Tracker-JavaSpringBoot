package com.apa.finance_tracker.services;

import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface JwtService {
    String generateAccessToken (Long sub);
    String generateRefreshToken (Long sub);
    boolean verifyAccessToken (String token) throws ParseException, JOSEException;
    boolean verifyRefreshToken (String token) throws ParseException, JOSEException;
}

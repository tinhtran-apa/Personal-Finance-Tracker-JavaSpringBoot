package com.apa.finance_tracker.services.impl;

import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.services.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.access-expiration}")
    private Integer accessExpiration;

    @Value("${jwt.access-secret}")
    private String accessSecretKey;

    @Value("${jwt.refresh-secret}")
    private String refreshSecretKey;

    @Value("${jwt.refresh-expiration}")
    private Integer refreshExpiration;

    @Override
    public String generateAccessToken(Long sub) {
        Date issueTime = new Date();
        Date expiratedTime = Date.from(issueTime.toInstant().plus(accessExpiration, ChronoUnit.MINUTES));
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(String.valueOf(sub))
                .issueTime(issueTime)
                .expirationTime(expiratedTime)
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        try {
            signedJWT.sign(new MACSigner(accessSecretKey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return signedJWT.serialize();
    }

    @Override
    public String generateRefreshToken(Long sub) {
        Date issueTime = new Date();
        Date expiratedTime = Date.from(issueTime.toInstant().plus(refreshExpiration, ChronoUnit.DAYS));
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(String.valueOf(sub))
                .issueTime(issueTime)
                .expirationTime(expiratedTime)
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        try {
            signedJWT.sign(new MACSigner(refreshSecretKey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return signedJWT.serialize();
    }

    @Override
    public boolean verifyAccessToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expiration.before(new Date())) {
            return false;
        }
        return signedJWT.verify(new MACVerifier(accessSecretKey));
    }

    @Override
    public boolean verifyRefreshToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expiration.before(new Date())) {
            return false;
        }
        return signedJWT.verify(new MACVerifier(refreshSecretKey));
    }
}

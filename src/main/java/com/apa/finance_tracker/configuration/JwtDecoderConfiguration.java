package com.apa.finance_tracker.configuration;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Objects;

import com.nimbusds.jose.JOSEException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Component
@RequiredArgsConstructor
public class JwtDecoderConfiguration implements JwtDecoder {
    @Value("${jwt.access-secret}")
    private String accessSecretKey;

    private final JwtService jwtService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;
    @Override
    public Jwt decode(String token) throws JwtException {
//        try {
//            if(!jwtService.verifyAccessToken(token)) {
//                throw new RuntimeException(ErrorMessage.TOKEN_INVALID);
//            }
//            if(Objects.isNull(nimbusJwtDecoder)) {{
//                SecretKey secretKey = new SecretKeySpec(accessSecretKey.getBytes(StandardCharsets.UTF_8), "HS256");
//                nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey)
//                        .macAlgorithm(MacAlgorithm.HS256)
//                        .build();
//            }}
//        } catch (ParseException | JOSEException e) {
//            throw new RuntimeException(e);
//        }
        if(Objects.isNull(nimbusJwtDecoder)) {{
                SecretKey secretKey = new SecretKeySpec(accessSecretKey.getBytes(StandardCharsets.UTF_8), "HS256");
                nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey)
                        .macAlgorithm(MacAlgorithm.HS256)
                        .build();
            }}
        return nimbusJwtDecoder.decode(token);
    }
}

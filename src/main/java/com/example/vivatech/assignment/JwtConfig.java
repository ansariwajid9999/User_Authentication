package com.example.vivatech.assignment;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long EXPIRATION_TIME = 3600_000;

    @Bean
    public SecretKey secretKey() {
        return SECRET_KEY;
    }

    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }
}

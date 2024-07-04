package com.example.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.business.application.libs.JWTMethod;

@Configuration
public class JWTConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Bean
    public JWTMethod jwtMethod() {
        return new JWTMethod(secretKey, jwtExpiration);
    }
}

package com.example.repositories.application.libs;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTMethod {
    //#region properties
    private String secretKey ;
    private long jwtExpiration ;
    //#endregion
    //#region constructor
    public JWTMethod (String secretKey, long expiration) {
        this.secretKey = secretKey;
        jwtExpiration = expiration;
    }
    //#endregion
    //#region Methods
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(Map<String, String> payload) {
        return Jwts
                .builder()
                .setClaims(payload)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    //#endregion
}

package com.example.ZeroWasteMessTracker.security;
import com.example.ZeroWasteMessTracker.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties){
        this.jwtProperties=jwtProperties;
    }

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

    }

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+jwtProperties.getExpiration()))
                .signWith(getSignKey())
                .compact();
    }

    public String extractUsername(String token){
        SecretKey key=Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}

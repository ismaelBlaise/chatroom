package com.ms.chatroom.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    private String secretKey = "mamamiaaa-ismael";  

    private long expirationTime = 86400000;  

     
    public String generateToken(String pseudo) {
        return JWT.create()
                .withSubject(pseudo)  
                .withIssuedAt(new Date()) 
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) 
                .sign(Algorithm.HMAC256(secretKey));  
    }

     
    public String extractPseudo(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getSubject();
    }

     
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

     
    public Date extractExpiration(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getExpiresAt();
    }

     
    public boolean validateToken(String token, String username) {
        return (username.equals(extractPseudo(token)) && !isTokenExpired(token));
    }
}

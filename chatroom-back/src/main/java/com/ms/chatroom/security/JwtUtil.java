package com.ms.chatroom.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    
    private String secretKey = "mamamiaaa-ismael"; 


    private long expirationTime = 86400000; 

   
    public String generateToken(String pseudo) {
        return Jwts.builder()
                .setSubject(pseudo) 
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) 
                .signWith(SignatureAlgorithm.HS256, secretKey) 
                .compact();
    }

    public String extractPseudo(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(extractPseudo(token)) && !isTokenExpired(token));
    }
}

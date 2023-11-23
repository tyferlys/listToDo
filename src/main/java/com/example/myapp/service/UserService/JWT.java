package com.example.myapp.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;

@Component
public class JWT {
        @Value("${jwt.secretKey}")
        private String SECRET_KEY;
        private int timeEnd = 1000 * 30; // сутки по плану

        public String getSECRET_KEY() {
            return SECRET_KEY;
        }

        public int getTimeEnd() {
            return timeEnd;
        }

        public String generateToken(String userName){
            return Jwts.builder()
                    .setSubject(userName)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + timeEnd))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        }

        public Claims decodeToken(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
}

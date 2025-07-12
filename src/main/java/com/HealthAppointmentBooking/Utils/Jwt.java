package com.HealthAppointmentBooking.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class Jwt {

    @Value("${jwt.secret}")
    private String signKey;

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(signKey));
    }

    public String generateToken(String userName,String role){
        return Jwts.builder()
                .claim("role",role)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}

package com.HealthAppointmentBooking.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class Jwt {

    @Value("${jwt.secret}")
    private  String signKey;

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

    public  String getUserName(ServerWebExchange exchange){
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null) {
            throw new RuntimeException("Missing Authorization header");
        }

        String token;
        if (authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else {
            token = authHeader; // assume raw token was sent
        }

        Claims claim = Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();

        return claim.getSubject();
    }
}

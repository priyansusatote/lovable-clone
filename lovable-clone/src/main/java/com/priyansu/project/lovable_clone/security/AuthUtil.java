package com.priyansu.project.lovable_clone.security;

import com.priyansu.project.lovable_clone.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

//This service acts as a utility class that isolates all cryptographic operations. It handles the low-level logic of interacting with the io.jsonwebtoken library

@Component
@RequiredArgsConstructor
public class AuthUtil {

    @Value("${jwt.secret-key}")
    private  String jwtSecretKey;

    //method (to convert Simple string to SecretKey) we use Algo named:hmacShaKey
    private SecretKey getSecretKey() {
       return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    //generate jwtToken
    public String generateAccessToken(User user){
        return  Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSecretKey())
                .compact();
    }

}

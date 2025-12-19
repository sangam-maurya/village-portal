package com.example.main.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    // ---------------- TOKEN GENERATE -------------------
    public String generateToken(String username) {
        return JWT.create()
                .withClaim("name", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    // ---------------- GET USERNAME ---------------------
    public String getUsername(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);

        return decodedJWT.getClaim("name").asString();
    }

    // ---------------- CHECK EXPIRATION -----------------
    public boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expiryDate = decodedJWT.getExpiresAt();
        return expiryDate.before(new Date());
    }

    // ---------------- VALIDATE TOKEN -------------------
    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        try {
            String username = getUsername(token);

            return (username.equals(userDetails.getUsername()) // username match
                    && !isTokenExpired(token));               // not expired

        } catch (Exception e) {
            return false;
        }
    }
}

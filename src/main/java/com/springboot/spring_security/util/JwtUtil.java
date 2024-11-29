package com.springboot.spring_security.util;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.springboot.spring_security.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class JwtUtil {
    @Value("${app.jwt.secret}")
    private String secret;
    @Value("${app.jwt.expiration}")
    private Long expiration;

    public String generateToken(User user) {
        return createToken(user);
    }

    public String createToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", user.getRoles())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    public boolean validateToken(String token, User user) {
        return isTokenValid(token) && isAuthoritiesValid(token, user);
    }

    public boolean isAuthoritiesValid(String token, User userDetails) {
        List<String> userAuthorities = userDetails.getRoles();

        return new HashSet<>(userAuthorities).equals(new HashSet<>(extractAuthorities(token)));
    }

    public boolean isTokenValid(String token) {
        return !extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);

    }

    @SuppressWarnings("unchecked")
    public List<String> extractAuthorities(String token) {

        return extractClaim(token, claim -> (List<String>) claim.get("authorities"));

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

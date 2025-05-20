package com.mbarca89.DenTracker.service.impl;

import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    private final String SECRET_KEY;
    public JwtServiceImpl(@Value("${jwt.secret}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    @Override
    public String getToken(UserDetails user, String clientId) {
        return getToken(new HashMap<>(), user, clientId);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String getUserNameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    @Override
    public <T> T getClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    @Override
    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private String getToken(Map<String,Object> extraClaims, UserDetails user, String clientId) {
        Client client = (Client) user;

        extraClaims.put("client_id", clientId);
        extraClaims.put("role", client.getRole().name());
        extraClaims.put("subscription", client.getSubscriptionStatus().name());

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(client.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000))) // 30 días
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String getClientIdFromToken(String token) {
        return getClaim(token, claims -> claims.get("client_id", String.class));
    }

    @Override
    public String getRoleFromToken(String token) {
        return getClaim(token, claims -> claims.get("role", String.class));
    }

    @Override
    public String getSubscriptionFromToken(String token) {
        return getClaim(token, claims -> claims.get("subscription", String.class));
    }
    @Override
    public Long getClientIdAsLong(String token) {
        return Long.valueOf(getClientIdFromToken(token));
    }

}

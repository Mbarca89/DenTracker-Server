package com.mbarca89.DenTracker.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    public String getToken(UserDetails user, String clientId);

    boolean isTokenValid(String token, UserDetails userDetails);

    public String getUserNameFromToken(String token);

    Claims getAllClaims(String token);

    <T> T getClaim(String token, Function<Claims, T> claimResolver);

    Date getExpiration(String token);

    boolean isTokenExpired(String token);

    String getClientIdFromToken(String token);

    String getRoleFromToken(String token);
    String getSubscriptionFromToken(String token);
    Long getClientIdAsLong(String token);
}

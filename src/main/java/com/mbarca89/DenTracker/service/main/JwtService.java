package com.mbarca89.DenTracker.service.main;

import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.main.Client;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
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
    boolean validateToken(String token);
    String generateToken(Map<String, Object> extraClaims, String subject);
    Long getClientUserIdFromToken(String token);
    String extractTokenFromRequest(HttpServletRequest request);
    String generateTokenForClientUser(Client client, ClientUser user);
}

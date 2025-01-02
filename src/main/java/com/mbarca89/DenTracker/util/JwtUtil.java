package com.mbarca89.DenTracker.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION_MS;

    // Generar el JWT
    public String generateToken(String username, String clientId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("clientId", clientId)  // Almacenamos el clientId como un claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Extraer username del token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Extraer el clientId desde los claims
    public String extractClientId(String token) {
        return extractClaims(token).get("clientId", String.class);
    }

    // Validar el token
    public boolean validateToken(String token) {
        try {
            extractClaims(token);  // Si no lanza excepción, el token es válido
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extraer los claims del token
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}

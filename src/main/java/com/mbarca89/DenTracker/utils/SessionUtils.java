package com.mbarca89.DenTracker.utils;

import com.mbarca89.DenTracker.service.main.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SessionUtils {

    /**
     * Retorna el ID del ClientUser autenticado (solo si el principal es un Long).
     */
    public static Long getAuthenticatedClientUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Long) {
            return (Long) auth.getPrincipal();
        }
        return null;
    }

    /**
     * Retorna true si hay un usuario autenticado y tiene el rol de CLIENT.
     */
    public static boolean isClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENT"));
    }

    /**
     * Retorna true si hay un usuario autenticado y tiene el rol de ADMIN.
     */
    public static boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    /**
     * Retorna true si hay un usuario autenticado y tiene el rol de subusuario (DENTIST, SECRETARY, etc.)
     */
    public static boolean isClientUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() instanceof Long;
    }

    /**
     * Retorna el email del cliente autenticado (solo si usa UserDetails).
     */
    public static String getAuthenticatedClientEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return null;
    }

    public static Long getClientId(HttpServletRequest request, JwtService jwtService) {
        try {
            String token = jwtService.extractTokenFromRequest(request);
            return jwtService.getClientIdAsLong(token);
        } catch (Exception e) {
            return null;
        }
    }
}


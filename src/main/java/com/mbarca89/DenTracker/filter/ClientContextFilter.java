package com.mbarca89.DenTracker.filter;

import com.mbarca89.DenTracker.context.ClientContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientContextFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Si el contexto de seguridad no tiene autenticación, tratamos el clientId de forma especial
        if (ClientContext.getClientId() == null) {
            // Si no hay clientId (lo que significa que no hay token JWT), asignamos el clientId por defecto
            ClientContext.setClientId("default"); // Redirigir a la base de datos general
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            // Limpiar el contexto al final de la solicitud
            ClientContext.clear();
        }
    }
}

package com.mbarca89.DenTracker.security;

import com.mbarca89.DenTracker.context.TenantContext;
import com.mbarca89.DenTracker.entity.base.Role;
import com.mbarca89.DenTracker.service.main.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        Optional<String> tokenOpt = getTokenFromRequest(request);
        if (tokenOpt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenOpt.get();

        try {
            // Verificamos validez del token
            if (!jwtService.validateToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
                return;
            }

            // Distinguimos entre CLIENT y CLIENT_USER según el contenido del token
            Long clientUserId = jwtService.getClientUserIdFromToken(token);

            if (clientUserId != null) {
                // 🔐 Token de ClientUser
                String role = jwtService.getRoleFromToken(token);
                Long clientId = jwtService.getClientIdAsLong(token); // por si lo querés usar

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        clientUserId,  // usamos el ID como principal
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } else {
                // 🔐 Token de Client (flujo original)
                String userName = jwtService.getUserNameFromToken(token);

                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                    if (jwtService.isTokenValid(token, userDetails)) {
                        String clientId = jwtService.getClientIdFromToken(token);
                        String role = jwtService.getRoleFromToken(token);

                        if (Role.CLIENT.name().equalsIgnoreCase(role)) {
                            TenantContext.setTenantId(Long.parseLong(clientId));
                        }

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            logger.warn("Token JWT inválido: {}", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
        } finally {
            TenantContext.clear();
        }
    }


    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}

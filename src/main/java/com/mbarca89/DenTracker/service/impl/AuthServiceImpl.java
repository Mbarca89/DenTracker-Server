package com.mbarca89.DenTracker.service.impl;

import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.repository.ClientRepository;
import com.mbarca89.DenTracker.entity.Client;
import com.mbarca89.DenTracker.dto.LoginRequest;
import com.mbarca89.DenTracker.dto.AuthResponse;
import com.mbarca89.DenTracker.service.AuthService;
import com.mbarca89.DenTracker.service.JwtService;
import com.mbarca89.DenTracker.util.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CryptoUtils cryptoUtils;

    @Override
    public AuthResponse login(LoginRequest request) throws ResourceNotFoundException {

        String decryptedPassword;
        try {
            decryptedPassword = cryptoUtils.decrypt(request.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar la contraseña", e);
        }

        // Autenticación del usuario
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), decryptedPassword));
        } catch (Exception e) {
            throw new RuntimeException("Error de inicio de sesión: " + e.getMessage(), e);
        }

        // Obtener el cliente por nombre de usuario
        Client client = clientRepository.findByUsername(request.getUsername());
        if (client == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        // Generar el token y crear la respuesta de autenticación
        String token = jwtService.getToken(client, client.getId().toString());
        AuthResponse response = new AuthResponse();
        response.setUserName(client.getUsername());
        response.setToken(token);
        response.setSubscriptionStatus(client.getAuthorities());
        response.setName(client.getClientName());
        response.setSurname(client.getClientSurname());
        response.setId(client.getId());
        return response;
    }
}


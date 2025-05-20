package com.mbarca89.DenTracker.service.impl;

import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.repository.ClientRepository;
import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.dto.response.LoginRequest;
import com.mbarca89.DenTracker.dto.response.AuthResponse;
import com.mbarca89.DenTracker.service.AuthService;
import com.mbarca89.DenTracker.service.JwtService;
import com.mbarca89.DenTracker.util.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final CryptoUtils cryptoUtils;

    @Override
    public AuthResponse login(LoginRequest request) {
        String decryptedPassword;
        try {
            decryptedPassword = cryptoUtils.decrypt(request.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar la contraseña");
        }

        Client client = clientRepository.findByUsername(request.getUsername());
        if (client == null) {
            throw new UsernameNotFoundException("El usuario no existe");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            decryptedPassword
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("La contraseña es incorrecta");
        } catch (Exception e) {
            throw new RuntimeException("Error al autenticar: " + e.getMessage());
        }

        String token = jwtService.getToken(client, client.getId().toString());

        AuthResponse response = new AuthResponse();
        response.setId(client.getId());
        response.setUserName(client.getUsername());
        response.setName(client.getClientName());
        response.setSurname(client.getClientSurname());
        response.setSubscriptionStatus(client.getSubscriptionStatus());
        response.setToken(token);

        return response;
    }
}



package com.mbarca89.DenTracker.service.main.impl;

import com.mbarca89.DenTracker.dto.request.main.ClientRequest;
import com.mbarca89.DenTracker.dto.request.main.LoginRequest;
import com.mbarca89.DenTracker.dto.response.main.AuthResponse;
import com.mbarca89.DenTracker.entity.enums.ClientStatus;
import com.mbarca89.DenTracker.entity.enums.Role;
import com.mbarca89.DenTracker.entity.enums.SubscriptionStatus;
import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.exception.InvalidCredentialsException;
import com.mbarca89.DenTracker.exception.UserAlreadyExistsException;
import com.mbarca89.DenTracker.repository.ClientRepository;
import com.mbarca89.DenTracker.service.main.AuthService;
import com.mbarca89.DenTracker.service.main.EmailService;
import com.mbarca89.DenTracker.service.main.JwtService;
import com.mbarca89.DenTracker.util.CryptoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final CryptoUtils cryptoUtils;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public AuthResponse login(LoginRequest request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Faltan credenciales de acceso");
        }

        String decryptedPassword;
        try {
            decryptedPassword = cryptoUtils.decrypt(request.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar la contraseña", e);
        }

        Client client = clientRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));

        if (client.getStatus() == ClientStatus.PENDING_EMAIL) {
            throw new IllegalStateException("Debe verificar su email antes de continuar");
        } else if (client.getStatus() == ClientStatus.SUSPENDED) {
            throw new IllegalStateException("Cuenta suspendida. Contacte al soporte");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            decryptedPassword
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Usuario o contraseña incorrectos");
        } catch (Exception e) {
            throw new RuntimeException("Error al autenticar: " + e.getMessage());
        }

        String token = jwtService.getToken(client, client.getId().toString());

        AuthResponse response = new AuthResponse();
        response.setId(client.getId());
        response.setUserName(client.getEmail());
        response.setName(client.getClientName());
        response.setSurname(client.getClientSurname());
        response.setSubscriptionStatus(client.getSubscriptionStatus());
        response.setToken(token);

        return response;
    }


    @Override
    public String verifyClient(String token) {
        Optional<Client> optionalClient = clientRepository.findByVerificationToken(token);

        if (optionalClient.isEmpty()) {
            throw new IllegalArgumentException("Token inválido o expirado");
        }

        Client client = optionalClient.get();
        client.setEmailVerified(true);
        client.setVerificationToken(null);
        client.setStatus(ClientStatus.PENDING_PAYMENT);

        clientRepository.save(client);

        return "Verificación completada. Ahora puede iniciar sesión para activar su plan.";
    }
}

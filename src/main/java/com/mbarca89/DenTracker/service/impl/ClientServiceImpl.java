package com.mbarca89.DenTracker.service.impl;

import com.mbarca89.DenTracker.dto.request.ClientRequest;
import com.mbarca89.DenTracker.dto.response.AuthResponse;
import com.mbarca89.DenTracker.dto.response.ClientResponse;
import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.entity.enums.Role;
import com.mbarca89.DenTracker.entity.enums.SubscriptionStatus;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.exception.UserAlreadyExistsException;
import com.mbarca89.DenTracker.repository.ClientRepository;
import com.mbarca89.DenTracker.service.ClientService;
import com.mbarca89.DenTracker.service.JwtService;
import com.mbarca89.DenTracker.util.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final CryptoUtils cryptoUtils;
    private final JwtService jwtService;

    @Override
    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron clientes.");
        }
        return clients.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuthResponse registerAndAuthenticateClient(ClientRequest request) {
        if (clientRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Ya existe un usuario con ese nombre.");
        }

        String decryptedPassword;
        try {
            decryptedPassword = cryptoUtils.decrypt(request.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar la contraseña", e);
        }

        String hashedPassword = passwordEncoder.encode(decryptedPassword);

        Client client = new Client();
        client.setClientName(request.getClientName());
        client.setClientSurname(request.getClientSurname());
        client.setUsername(request.getUsername());
        client.setPassword(hashedPassword);
        client.setCreatedAt(LocalDateTime.now());
        client.setRole(Role.CLIENT);
        client.setSubscriptionStatus(SubscriptionStatus.STANDARD); // O configurable

        clientRepository.save(client);

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

    private ClientResponse mapToResponse(Client client) {
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setClientName(client.getClientName());
        response.setClientSurname(client.getClientSurname());
        response.setUsername(client.getUsername());
        response.setSubscriptionStatus(client.getSubscriptionStatus());
        response.setCreatedAt(client.getCreatedAt().toString());
        return response;
    }
}

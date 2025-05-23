package com.mbarca89.DenTracker.service.client.Impl;

import com.mbarca89.DenTracker.dto.request.client.ClientChangePasswordRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUpdateRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ClientProfileResponseDto;
import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.exception.InvalidCredentialsException;
import com.mbarca89.DenTracker.repository.main.ClientRepository;
import com.mbarca89.DenTracker.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientProfileResponseDto getMyProfile(Client authenticatedClient) {
        ClientProfileResponseDto dto = new ClientProfileResponseDto();
        dto.setId(authenticatedClient.getId());
        dto.setClientName(authenticatedClient.getClientName());
        dto.setClientSurname(authenticatedClient.getClientSurname());
        dto.setEmail(authenticatedClient.getEmail());
        dto.setPhone(authenticatedClient.getPhone());
        dto.setSubscriptionStatus(authenticatedClient.getSubscriptionStatus());
        dto.setStatus(authenticatedClient.getStatus());
        dto.setCreatedAt(authenticatedClient.getCreatedAt());
        return dto;
    }

    @Override
    public void changePassword(Client client, ClientChangePasswordRequestDto request) {
        // Verifica que la contraseña actual coincida
        if (!passwordEncoder.matches(request.getCurrentPassword(), client.getPassword())) {
            throw new InvalidCredentialsException("La contraseña actual es incorrecta.");
        }

        // Hashea la nueva contraseña
        String hashed = passwordEncoder.encode(request.getNewPassword());
        client.setPassword(hashed);
        clientRepository.save(client);
    }

    @Override
    public ClientProfileResponseDto updateMyProfile(Client client, ClientUpdateRequestDto request) {
        client.setClientName(request.getClientName());
        client.setClientSurname(request.getClientSurname());
        client.setPhone(request.getPhone());

        clientRepository.save(client);

        return getMyProfile(client);
    }

}

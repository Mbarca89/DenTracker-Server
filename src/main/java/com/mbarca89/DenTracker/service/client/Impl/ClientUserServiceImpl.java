package com.mbarca89.DenTracker.service.client.Impl;

import com.mbarca89.DenTracker.dto.request.client.ClientUserCreateRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUserSelectionRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUserUpdateRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserResponseDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserSelfResponseDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserTokenResponseDto;
import com.mbarca89.DenTracker.dto.response.main.AuthResponse;
import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.exception.InvalidCredentialsException;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.client.ClientUserMapper;
import com.mbarca89.DenTracker.repository.client.ClientUserRepository;
import com.mbarca89.DenTracker.service.client.ClientUserService;
import com.mbarca89.DenTracker.service.main.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientUserServiceImpl implements ClientUserService {

    private final ClientUserRepository clientUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ClientUserMapper clientUserMapper;

    @Override
    public ClientUserTokenResponseDto authenticateClientUser(ClientUserSelectionRequestDto request, Client client) throws AccessDeniedException {
        ClientUser user = clientUserRepository.findByIdAndActiveTrue(request.getClientUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Subusuario no encontrado o inactivo."));

        if (!user.getClient().getId().equals(client.getId())) {
            throw new AccessDeniedException("Este subusuario no pertenece a tu cuenta.");
        }

        if (!passwordEncoder.matches(request.getPin(), user.getPin())) {
            throw new InvalidCredentialsException("PIN incorrecto.");
        }

        String token = jwtService.generateTokenForClientUser(client, user);

        ClientUserTokenResponseDto dto = new ClientUserTokenResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        dto.setToken(token);
        return dto;
    }


    @Override
    public ClientUser createClientUser(ClientUserCreateRequestDto request, Client authenticatedClient) {
        // Validación simple del PIN
        if (request.getPin() == null || request.getPin().length() != 4 || !request.getPin().matches("\\d{4}")) {
            throw new IllegalArgumentException("El PIN debe ser un número de 4 dígitos.");
        }

        // Crear y guardar el nuevo ClientUser
        ClientUser user = new ClientUser();
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setPin(passwordEncoder.encode(request.getPin()));
        user.setClient(authenticatedClient);
        user.setActive(true);
        return clientUserRepository.save(user);
    }

    @Override
    public List<ClientUserResponseDto> getActiveClientUsers(Long clientId) {
        List<ClientUser> users = clientUserRepository.findAllByClientIdAndActiveTrue(clientId);
        return clientUserMapper.toDtoList(users);
    }

    @Override
    public ClientUserResponseDto updateClientUser(Long userId, ClientUserUpdateRequestDto request, Client client) throws AccessDeniedException {
        ClientUser user = clientUserRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe o está inactivo."));

        if (!user.getClient().getId().equals(client.getId())) {
            throw new AccessDeniedException("No puedes modificar un usuario que no te pertenece.");
        }

        user.setName(request.getName());

        if (request.getNewPin() != null && !request.getNewPin().isBlank()) {
            if (!request.getNewPin().matches("\\d{4}")) {
                throw new IllegalArgumentException("El PIN debe ser un número de 4 dígitos.");
            }
            user.setPin(passwordEncoder.encode(request.getNewPin()));
        }

        clientUserRepository.save(user);
        return clientUserMapper.toDto(user);
    }

    @Override
    public void deleteClientUser(Long userId, Client client) throws AccessDeniedException {
        ClientUser user = clientUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));

        if (!user.getClient().getId().equals(client.getId())) {
            throw new AccessDeniedException("No tienes permiso para eliminar este usuario.");
        }

        user.setActive(false);
        clientUserRepository.save(user);
    }

    @Override
    public ClientUserSelfResponseDto getCurrentClientUser(Long clientUserId) {
        ClientUser user = clientUserRepository.findByIdAndActiveTrue(clientUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado o inactivo."));

        ClientUserSelfResponseDto dto = new ClientUserSelfResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        return dto;
    }

}


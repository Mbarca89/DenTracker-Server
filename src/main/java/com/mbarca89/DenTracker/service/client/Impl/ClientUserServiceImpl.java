package com.mbarca89.DenTracker.service.client.Impl;

import com.mbarca89.DenTracker.dto.request.client.ClientUserCreateRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUserSelectionRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserResponseDto;
import com.mbarca89.DenTracker.dto.response.main.AuthResponse;
import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.client.ClientUserMapper;
import com.mbarca89.DenTracker.repository.ClientUserRepository;
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
    public AuthResponse selectClientUser(ClientUserSelectionRequestDto request, Client authenticatedClient) throws AccessDeniedException {

        // 1. Buscar subusuario activo
        ClientUser clientUser = clientUserRepository.findByIdAndActiveTrue(request.getClientUserId())
                .orElseThrow(() -> new ResourceNotFoundException("El subusuario no existe o está inactivo."));

        // 2. Verificar que pertenezca al cliente autenticado
        if (!clientUser.getClient().getId().equals(authenticatedClient.getId())) {
            throw new AccessDeniedException("Este subusuario no pertenece al cliente autenticado.");
        }

        // 3. Verificar PIN
        if (!passwordEncoder.matches(request.getPin(), clientUser.getPin())) {
            throw new BadCredentialsException("PIN incorrecto.");
        }

        // 4. Crear claims para el JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("clientId", authenticatedClient.getId());
        claims.put("clientUserId", clientUser.getId());
        claims.put("role", clientUser.getRole());

        // 5. Generar token
        String token = jwtService.generateToken(claims, clientUser.getName());

        // 6. Retornar respuesta
        AuthResponse response = new AuthResponse();
        response.setId(clientUser.getId());
        response.setName(clientUser.getName());
        response.setSurname(null); // Si no tenés campo surname en ClientUser
        response.setUserName(clientUser.getName());
        response.setToken(token);
        response.setSubscriptionStatus(authenticatedClient.getSubscriptionStatus());

        return response;
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


}


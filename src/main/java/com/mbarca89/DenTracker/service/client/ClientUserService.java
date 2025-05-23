package com.mbarca89.DenTracker.service.client;

import com.mbarca89.DenTracker.dto.request.client.ClientUserCreateRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUserSelectionRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUserUpdateRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserResponseDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserSelfResponseDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserTokenResponseDto;
import com.mbarca89.DenTracker.dto.response.main.AuthResponse;
import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.main.Client;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ClientUserService {
    ClientUserTokenResponseDto authenticateClientUser(ClientUserSelectionRequestDto request, Client client) throws AccessDeniedException;
    ClientUser createClientUser(ClientUserCreateRequestDto request, Client authenticatedClient);
    List<ClientUserResponseDto> getActiveClientUsers(Long clientId);
    ClientUserResponseDto updateClientUser(Long userId, ClientUserUpdateRequestDto request, Client client) throws AccessDeniedException;
    void deleteClientUser(Long userId, Client client) throws AccessDeniedException;
    ClientUserSelfResponseDto getCurrentClientUser(Long clientUserId);
}


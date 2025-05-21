package com.mbarca89.DenTracker.service.client;

import com.mbarca89.DenTracker.dto.request.client.ClientUserCreateRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUserSelectionRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserResponseDto;
import com.mbarca89.DenTracker.dto.response.main.AuthResponse;
import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.main.Client;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ClientUserService {
    AuthResponse selectClientUser(ClientUserSelectionRequestDto request, Client authenticatedClient) throws AccessDeniedException;
    ClientUser createClientUser(ClientUserCreateRequestDto request, Client authenticatedClient);
    List<ClientUserResponseDto> getActiveClientUsers(Long clientId);

}


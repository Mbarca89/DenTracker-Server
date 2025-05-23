package com.mbarca89.DenTracker.service.client;

import com.mbarca89.DenTracker.dto.request.client.ClientChangePasswordRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUpdateRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ClientProfileResponseDto;
import com.mbarca89.DenTracker.entity.main.Client;

public interface ClientService {
    ClientProfileResponseDto getMyProfile(Client authenticatedClient);
    void changePassword(Client authenticatedClient, ClientChangePasswordRequestDto request);
    ClientProfileResponseDto updateMyProfile(Client client, ClientUpdateRequestDto request);

}

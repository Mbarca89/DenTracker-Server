package com.mbarca89.DenTracker.service.main;

import com.mbarca89.DenTracker.dto.request.main.ClientDetailResponseDto;
import com.mbarca89.DenTracker.dto.request.main.ClientRequest;
import com.mbarca89.DenTracker.dto.response.main.AuthResponse;
import com.mbarca89.DenTracker.dto.response.main.ClientResponse;
import com.mbarca89.DenTracker.dto.response.main.ClientStatsResponseDto;

import java.util.List;

public interface MainClientService {
    List<ClientResponse> getAllClients();
    AuthResponse registerAndAuthenticateClient(ClientRequest request);
    void deleteClientById(Long id);
    ClientDetailResponseDto getClientById(Long id);
    ClientStatsResponseDto getClientStats();

}

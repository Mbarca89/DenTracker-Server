package com.mbarca89.DenTracker.service.main;

import com.mbarca89.DenTracker.dto.request.main.ClientRequest;
import com.mbarca89.DenTracker.dto.response.main.AuthResponse;
import com.mbarca89.DenTracker.dto.response.main.ClientResponse;

import java.util.List;

public interface ClientService {
    List<ClientResponse> getAllClients();
    AuthResponse registerAndAuthenticateClient(ClientRequest request);
}

package com.mbarca89.DenTracker.service;

import com.mbarca89.DenTracker.dto.ClientRequest;
import com.mbarca89.DenTracker.dto.ClientResponse;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ClientService {
    List<ClientResponse> getAllClients();
    ClientResponse registerNewClient(ClientRequest request) throws NoSuchAlgorithmException;
}

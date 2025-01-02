package com.mbarca89.DenTracker.controller;

import com.mbarca89.DenTracker.dto.ClientRequest;
import com.mbarca89.DenTracker.dto.ClientResponse;
import com.mbarca89.DenTracker.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientServiceImpl service;

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> clients = service.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest request) throws NoSuchAlgorithmException {
        ClientResponse response = service.registerNewClient(request);
        return ResponseEntity.ok(response);
    }
}

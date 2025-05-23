package com.mbarca89.DenTracker.controller.publicController;

import com.mbarca89.DenTracker.dto.request.main.ClientRequest;
import com.mbarca89.DenTracker.dto.response.main.AuthResponse;
import com.mbarca89.DenTracker.service.main.MainClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/clients")
@RequiredArgsConstructor
public class PublicClientController {

    private final MainClientService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerClient(@RequestBody @Valid ClientRequest request) {
        AuthResponse response = service.registerAndAuthenticateClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}


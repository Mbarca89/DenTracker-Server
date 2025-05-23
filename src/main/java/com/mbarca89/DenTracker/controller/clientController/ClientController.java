package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.client.ClientChangePasswordRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUpdateRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ClientProfileResponseDto;
import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.service.client.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/clients/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @GetMapping("/me")
    public ResponseEntity<ClientProfileResponseDto> getMyProfile(@AuthenticationPrincipal Client client) {
        return ResponseEntity.ok(service.getMyProfile(client));
    }

    @PutMapping("/me/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @AuthenticationPrincipal Client client,
            @RequestBody @Valid ClientChangePasswordRequestDto request) {

        service.changePassword(client, request);
        return ResponseEntity.ok(Map.of("message", "La contraseña se ha cambiado correctamente."));
    }

    @PutMapping("/me")
    public ResponseEntity<ClientProfileResponseDto> updateMyProfile(
            @AuthenticationPrincipal Client client,
            @RequestBody @Valid ClientUpdateRequestDto request) {

        return ResponseEntity.ok(service.updateMyProfile(client, request));
    }



}

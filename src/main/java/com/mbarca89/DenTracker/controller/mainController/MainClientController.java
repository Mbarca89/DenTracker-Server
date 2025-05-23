package com.mbarca89.DenTracker.controller.mainController;

import com.mbarca89.DenTracker.dto.request.main.ClientDetailResponseDto;
import com.mbarca89.DenTracker.dto.response.main.ClientResponse;
import com.mbarca89.DenTracker.dto.response.main.ClientStatsResponseDto;
import com.mbarca89.DenTracker.service.main.impl.MainClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/clients")
public class MainClientController {

    @Autowired
    private MainClientServiceImpl service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> clients = service.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteClient(@PathVariable Long id) {
        service.deleteClientById(id);
        return ResponseEntity.ok(Map.of("message", "Cliente eliminado correctamente."));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientDetailResponseDto> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getClientById(id));
    }
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientStatsResponseDto> getClientStats() {
        return ResponseEntity.ok(service.getClientStats());
    }

}

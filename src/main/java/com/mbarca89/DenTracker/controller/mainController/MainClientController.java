package com.mbarca89.DenTracker.controller.mainController;

import com.mbarca89.DenTracker.dto.response.main.ClientResponse;
import com.mbarca89.DenTracker.service.main.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/clients")
public class MainClientController {

    @Autowired
    private ClientServiceImpl service;

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> clients = service.getAllClients();
        return ResponseEntity.ok(clients);
    }
}

package com.mbarca89.DenTracker.controller.publicController;

import com.mbarca89.DenTracker.dto.request.ClientRequest;
import com.mbarca89.DenTracker.dto.response.AuthResponse;
import com.mbarca89.DenTracker.dto.response.ClientResponse;
import com.mbarca89.DenTracker.exception.UserAlreadyExistsException;
import com.mbarca89.DenTracker.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/clients")
public class ClientController {

    @Autowired
    private ClientServiceImpl service;

    @PostMapping("/regiser")
    public ResponseEntity<AuthResponse> registerClient(@RequestBody ClientRequest request) {
        try {
            AuthResponse response = service.registerAndAuthenticateClient(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

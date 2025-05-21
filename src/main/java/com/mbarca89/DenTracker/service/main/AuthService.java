package com.mbarca89.DenTracker.service.main;

import com.mbarca89.DenTracker.dto.request.main.ClientRequest;
import com.mbarca89.DenTracker.dto.response.main.AuthResponse;
import com.mbarca89.DenTracker.dto.request.main.LoginRequest;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;

public interface AuthService {

    AuthResponse login(LoginRequest request) throws ResourceNotFoundException;

    String verifyClient(String token);

}
package com.mbarca89.DenTracker.service;

import com.mbarca89.DenTracker.dto.AuthResponse;
import com.mbarca89.DenTracker.dto.LoginRequest;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;

public interface AuthService {
    public AuthResponse login (LoginRequest request) throws ResourceNotFoundException;
}
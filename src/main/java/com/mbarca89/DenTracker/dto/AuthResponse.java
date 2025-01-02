package com.mbarca89.DenTracker.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class AuthResponse {
    Long id;
    String name;
    String surname;
    String token;
    String userName;
    Collection subscriptionStatus;
}
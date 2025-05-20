package com.mbarca89.DenTracker.dto.response;

import com.mbarca89.DenTracker.entity.enums.SubscriptionStatus;
import lombok.Data;

import java.util.Collection;

@Data
public class AuthResponse {
    private Long id;
    private String name;
    private String surname;
    private String token;
    private String userName;
    private SubscriptionStatus subscriptionStatus;
}
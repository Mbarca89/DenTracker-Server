package com.mbarca89.DenTracker.dto.response.main;

import com.mbarca89.DenTracker.entity.base.SubscriptionStatus;
import lombok.Data;

@Data
public class AuthResponse {
    private Long id;
    private String name;
    private String surname;
    private String token;
    private String userName;
    private SubscriptionStatus subscriptionStatus;
}
package com.mbarca89.DenTracker.dto.response;

import com.mbarca89.DenTracker.entity.enums.SubscriptionStatus;
import lombok.Data;

@Data
public class ClientResponse {
    private Long id;
    private String clientName;
    private String clientSurname;
    private String username;
    private SubscriptionStatus subscriptionStatus;
    private String createdAt;
}

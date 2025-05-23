package com.mbarca89.DenTracker.dto.request.main;

import com.mbarca89.DenTracker.entity.base.ClientStatus;
import com.mbarca89.DenTracker.entity.base.SubscriptionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientDetailResponseDto {
    private Long id;
    private String clientName;
    private String clientSurname;
    private String email;
    private String phone;
    private SubscriptionStatus subscriptionStatus;
    private ClientStatus status;
    private LocalDateTime createdAt;
    private int userCount;
}

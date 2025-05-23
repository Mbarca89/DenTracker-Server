package com.mbarca89.DenTracker.dto.response.main;

import com.mbarca89.DenTracker.entity.base.SubscriptionStatus;
import lombok.Data;

import java.util.Map;

@Data
public class ClientStatsResponseDto {
    private long totalClients;
    private long activeClients;
    private long pendingClients;
    private Map<SubscriptionStatus, Long> clientsPerPlan;
    private double averageUsersPerClient;
}

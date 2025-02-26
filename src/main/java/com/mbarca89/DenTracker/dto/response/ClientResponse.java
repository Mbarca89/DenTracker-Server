package com.mbarca89.DenTracker.dto.response;

import lombok.Data;

@Data
public class ClientResponse {
    private Long id;
    private String clientName;
    private String clientSurname;
    private String username;
    private String databaseUrl;
    private String subscriptionStatus;
    private String createdAt;
}

package com.mbarca89.DenTracker.dto;

import lombok.Data;

@Data
public class ClientRequest {
    private Long id;
    private String clientName;
    private String clientSurname;
    private String databaseUrl;
    private String username;
    private String password;
    private String subscriptionStatus;
}
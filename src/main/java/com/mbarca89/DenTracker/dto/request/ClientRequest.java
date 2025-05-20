package com.mbarca89.DenTracker.dto.request;

import lombok.Data;

@Data
public class ClientRequest {
    private String clientName;
    private String clientSurname;
    private String username;
    private String password;
}
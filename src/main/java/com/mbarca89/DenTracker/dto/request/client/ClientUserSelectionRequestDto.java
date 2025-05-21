package com.mbarca89.DenTracker.dto.request.client;

import lombok.Data;

@Data
public class ClientUserSelectionRequestDto {
    private Long clientUserId;
    private String pin;
}

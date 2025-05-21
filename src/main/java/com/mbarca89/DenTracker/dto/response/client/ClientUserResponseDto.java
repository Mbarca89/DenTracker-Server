package com.mbarca89.DenTracker.dto.response.client;

import com.mbarca89.DenTracker.entity.enums.ClientUserRole;
import lombok.Data;

@Data
public class ClientUserResponseDto {
    private Long id;
    private String name;
    private ClientUserRole role;
}

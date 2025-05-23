package com.mbarca89.DenTracker.dto.response.client;

import com.mbarca89.DenTracker.entity.base.ClientUserRole;
import lombok.Data;

@Data
public class ClientUserTokenResponseDto {
    private Long id;
    private String name;
    private ClientUserRole role;
    private String token;
}

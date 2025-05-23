package com.mbarca89.DenTracker.dto.request.client;

import com.mbarca89.DenTracker.entity.base.ClientUserRole;
import lombok.Data;

@Data
public class ClientUserCreateRequestDto {
    private String name;
    private ClientUserRole role;
    private String pin;  // PIN en texto plano (luego se hashea)
}

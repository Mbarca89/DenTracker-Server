package com.mbarca89.DenTracker.dto.request.client;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientUserUpdateRequestDto {
    @NotBlank(message = "El nombre es obligatorio.")
    private String name;

    private String newPin; // opcional
}

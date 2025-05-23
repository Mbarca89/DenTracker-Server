package com.mbarca89.DenTracker.dto.request.client;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientChangePasswordRequestDto {

    @NotBlank(message = "La contraseña actual es obligatoria.")
    private String currentPassword;

    @NotBlank(message = "La nueva contraseña es obligatoria.")
    private String newPassword;
}

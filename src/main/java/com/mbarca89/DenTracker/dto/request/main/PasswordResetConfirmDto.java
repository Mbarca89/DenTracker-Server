package com.mbarca89.DenTracker.dto.request.main;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetConfirmDto {

    @NotBlank(message = "El token es obligatorio.")
    private String token;

    @NotBlank(message = "La nueva contraseña es obligatoria.")
    private String newPassword;
}

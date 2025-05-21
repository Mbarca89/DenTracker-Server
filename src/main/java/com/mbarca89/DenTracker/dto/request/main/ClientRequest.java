package com.mbarca89.DenTracker.dto.request.main;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientRequest {

    @NotBlank(message = "El nombre es obligatorio.")
    private String clientName;

    @NotBlank(message = "El apellido es obligatorio.")
    private String clientSurname;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El email no tiene un formato válido.")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String password;

    @NotBlank(message = "El teléfono es obligatorio.")
    private String phone;
}

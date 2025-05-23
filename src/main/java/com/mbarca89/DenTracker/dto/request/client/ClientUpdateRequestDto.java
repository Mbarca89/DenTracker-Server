package com.mbarca89.DenTracker.dto.request.client;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientUpdateRequestDto {

    @NotBlank(message = "El nombre es obligatorio.")
    private String clientName;

    @NotBlank(message = "El apellido es obligatorio.")
    private String clientSurname;

    private String phone;
}

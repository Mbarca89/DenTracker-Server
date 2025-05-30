package com.mbarca89.DenTracker.dto.request.client;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequestDto {
    @NotNull(message = "El nombre es obligatorio.")
    private String firstName;
    @NotNull(message = "El apellido es obligatorio.")
    private String lastName;
    private String phone;
    @NotNull(message = "La fecha de inicio es obligatoria.")
    @FutureOrPresent(message = "La fecha de inicio debe ser en el presente o futuro.")
    private LocalDateTime startDate;

    @NotNull(message = "La fecha de fin es obligatoria.")
    @FutureOrPresent(message = "La fecha de fin debe ser en el presente o futuro.")
    private LocalDateTime endDate;
    private String title;
}

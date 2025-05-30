package com.mbarca89.DenTracker.entity.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio.")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio.")
    private String lastName;

    private String phone;

    @Email
    private String email;

    @NotNull(message = "La fecha de inicio es obligatoria.")
    @FutureOrPresent(message = "La fecha de inicio debe ser en el presente o futuro.")
    private LocalDateTime startDate;

    @NotNull(message = "La fecha de fin es obligatoria.")
    @FutureOrPresent(message = "La fecha de fin debe ser en el presente o futuro.")
    private LocalDateTime endDate;

    private String title;

    @Column(columnDefinition = "boolean default false")
    private Boolean messageSent;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(nullable = false)
    private Long clientId;
}

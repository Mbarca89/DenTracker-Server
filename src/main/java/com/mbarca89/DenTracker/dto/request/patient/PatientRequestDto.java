package com.mbarca89.DenTracker.dto.request.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequestDto {

    @NotBlank(message = "El nombre es obligatorio.")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio.")
    private String lastName;

    @NotBlank(message = "El tipo de documento es obligatorio.")
    private String docType;

    @NotBlank(message = "El número de documento es obligatorio.")
    private String docNumber;

    @Past(message = "La fecha de nacimiento debe ser en el pasado.")
    private LocalDate birthDate;

    private String phone;

    @Email(message = "El email no es válido.")
    private String email;

    private String address;

    private String city;

    private String province;

    private String country;

    private String gender;

    private String observations;
}

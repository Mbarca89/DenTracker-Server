package com.mbarca89.DenTracker.dto.request.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDto {
    private Long id;
    @NotBlank(message = "Debe especificar el profesional a cargo.")
    private String inChargeOf;
    @NotNull(message = "Debe especificar el ID del profesional a cargo.")
    private Long inChargeOfId;
    @NotBlank(message = "Ingrese el nombre")
    private String name;
    @NotBlank(message = "Ingrese el apellido")
    private String surname;
    @NotBlank(message = "Debe especificar el tipo de documento")
    private String docType;
    @NotNull(message = "Ingrese el número de documento")
    private Long doc;
    @NotBlank(message = "Ingrese el género")
    private String gender;
    @NotNull(message = "Ingrese la fecha de nacimiento")
    private Date birth;
    @NotBlank(message = "Ingrese la nacionalidad")
    private String nationality;
    @NotBlank(message = "Ingrese el estado civil")
    private String civilState;
    @NotBlank(message = "Ingrese el país")
    private String country;
    @NotBlank(message = "Ingrese la provincia")
    private String state;
    @NotBlank(message = "Ingrese la ciudad")
    private String city;
    @NotBlank(message = "Ingrese la dirección")
    private String address;
    private String derivedBy;
    private Long phone;
    private String email;
    private String occupation;
    private String studies;
    private String workAddress;
    private String workingHours;
    private String social;
    private Long socialNumber;
    private String observations;
}

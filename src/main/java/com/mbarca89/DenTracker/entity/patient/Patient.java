package com.mbarca89.DenTracker.entity.patient;

import com.mbarca89.DenTracker.entity.base.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
public class Patient extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "El tipo de documento es obligatorio.")
    @Column(name = "doc_type", nullable = false)
    private String docType;

    @NotBlank(message = "El número de documento es obligatorio.")
    @Column(name = "doc_number", nullable = false, unique = true)
    private String docNumber;

    @Past(message = "La fecha de nacimiento debe ser en el pasado.")
    @Column(name = "birth_date")
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

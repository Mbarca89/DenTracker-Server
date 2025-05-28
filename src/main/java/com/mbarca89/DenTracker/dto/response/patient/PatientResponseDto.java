package com.mbarca89.DenTracker.dto.response.patient;

import com.mbarca89.DenTracker.dto.response.client.CreatedByUserDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDto {

    private Long id;

    private String firstName;
    private String lastName;
    private String docType;
    private String docNumber;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String province;
    private String country;
    private String gender;
    private String observations;
    private CreatedByUserDto createdBy;

    private boolean active;
    private String createdAt;
}

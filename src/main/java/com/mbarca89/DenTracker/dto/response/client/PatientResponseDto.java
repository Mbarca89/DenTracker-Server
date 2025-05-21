package com.mbarca89.DenTracker.dto.response.client;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto {
    private Long id;
    @Lob
    private byte[] image;
    private String inChargeOf;
    private Long inChargeOfId;
    private String name;
    private String surname;
    private String docType;
    private Long doc;
    private String gender;
    private Date birth;
    private String nationality;
    private String civilState;
    private String country;
    private String state;
    private String city;
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

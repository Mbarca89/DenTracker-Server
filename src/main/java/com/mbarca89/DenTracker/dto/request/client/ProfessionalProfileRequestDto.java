package com.mbarca89.DenTracker.dto.request.client;

import lombok.Data;

@Data
public class ProfessionalProfileRequestDto {
    private String fullName;
    private String profileImageBase64;
    private String registrationNumber;
    private String documentNumber;
    private String phone;
    private String email;
}

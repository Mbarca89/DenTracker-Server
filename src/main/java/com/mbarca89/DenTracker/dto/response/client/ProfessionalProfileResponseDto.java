package com.mbarca89.DenTracker.dto.response.client;

import lombok.Data;

@Data
public class ProfessionalProfileResponseDto {
    private Long id;
    private String fullName;
    private String profileImageBase64;
    private String registrationNumber;
    private String documentNumber;
    private String phone;
    private String email;
}

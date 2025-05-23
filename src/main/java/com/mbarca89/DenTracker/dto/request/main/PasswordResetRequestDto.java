package com.mbarca89.DenTracker.dto.request.main;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequestDto {
    @Email
    @NotBlank
    private String email;
}


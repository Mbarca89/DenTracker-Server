package com.mbarca89.DenTracker.service.client;

import com.mbarca89.DenTracker.dto.request.client.ProfessionalProfileRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ProfessionalProfileResponseDto;

public interface ProfessionalProfileService {
    ProfessionalProfileResponseDto createOrUpdateProfile(Long userId, ProfessionalProfileRequestDto requestDto);
    ProfessionalProfileResponseDto getProfile(Long userId);
}

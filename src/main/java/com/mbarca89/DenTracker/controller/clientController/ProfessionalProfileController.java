package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.client.ProfessionalProfileRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ProfessionalProfileResponseDto;
import com.mbarca89.DenTracker.service.client.ProfessionalProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/professional-profiles")
@RequiredArgsConstructor
public class ProfessionalProfileController {

    private final ProfessionalProfileService professionalProfileService;

    @PostMapping("/{userId}")
    public ProfessionalProfileResponseDto createOrUpdateProfile(
            @PathVariable Long userId,
            @RequestBody ProfessionalProfileRequestDto requestDto) {
        return professionalProfileService.createOrUpdateProfile(userId, requestDto);
    }

    @GetMapping("/{userId}")
    public ProfessionalProfileResponseDto getProfile(@PathVariable Long userId) {
        return professionalProfileService.getProfile(userId);
    }
}

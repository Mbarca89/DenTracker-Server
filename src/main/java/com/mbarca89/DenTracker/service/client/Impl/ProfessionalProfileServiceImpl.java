package com.mbarca89.DenTracker.service.client.Impl;

import com.mbarca89.DenTracker.dto.request.client.ProfessionalProfileRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ProfessionalProfileResponseDto;
import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.client.ProfessionalProfile;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.client.ProfessionalProfileMapper;
import com.mbarca89.DenTracker.repository.client.ClientUserRepository;
import com.mbarca89.DenTracker.repository.client.ProfessionalProfileRepository;
import com.mbarca89.DenTracker.service.client.ProfessionalProfileService;
import com.mbarca89.DenTracker.utils.ImageCompressor;
import com.mbarca89.DenTracker.utils.Images;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ProfessionalProfileServiceImpl implements ProfessionalProfileService {

    private final ProfessionalProfileRepository profileRepository;
    private final ClientUserRepository clientUserRepository;
    private final ProfessionalProfileMapper profileMapper;
    private final ImageCompressor imageCompressor;

    @Transactional
    @Override
    public ProfessionalProfileResponseDto createOrUpdateProfile(Long userId, ProfessionalProfileRequestDto requestDto) {
        ClientUser user = clientUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + userId));

        ProfessionalProfile profile = profileRepository.findByUserId(userId)
                .orElse(new ProfessionalProfile());
        profile.setUser(user);

        profileMapper.updateEntityFromDto(requestDto, profile);

        if (requestDto.getProfileImageBase64() != null && !requestDto.getProfileImageBase64().isEmpty()) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(requestDto.getProfileImageBase64());
                Images images = imageCompressor.compressImage(imageBytes, false, null, null);
                byte[] thumbnailBytes = images.getThumbnail();
                String thumbnailBase64 = Base64.getEncoder().encodeToString(thumbnailBytes);
                profile.setProfileImageBase64(thumbnailBase64);
            } catch (IOException e) {
                throw new RuntimeException("Error al comprimir la imagen", e);
            }
        }

        ProfessionalProfile savedProfile = profileRepository.save(profile);
        return profileMapper.toResponseDto(savedProfile);
    }

    @Override
    public ProfessionalProfileResponseDto getProfile(Long userId) {
        ProfessionalProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil profesional no encontrado para el usuario con id: " + userId));
        return profileMapper.toResponseDto(profile);
    }

}

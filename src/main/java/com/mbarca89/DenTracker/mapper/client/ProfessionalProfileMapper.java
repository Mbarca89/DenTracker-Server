package com.mbarca89.DenTracker.mapper.client;

import com.mbarca89.DenTracker.dto.request.client.ProfessionalProfileRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ProfessionalProfileResponseDto;
import com.mbarca89.DenTracker.entity.client.ProfessionalProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfessionalProfileMapper {

    ProfessionalProfileResponseDto toResponseDto(ProfessionalProfile professionalProfile);

    ProfessionalProfile toEntity(ProfessionalProfileRequestDto requestDto);

    void updateEntityFromDto(ProfessionalProfileRequestDto requestDto, @MappingTarget ProfessionalProfile professionalProfile);
}

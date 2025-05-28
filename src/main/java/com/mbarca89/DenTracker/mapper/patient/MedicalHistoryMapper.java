package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.request.patient.MedicalHistoryRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.MedicalHistoryResponseDto;
import com.mbarca89.DenTracker.entity.patient.MedicalHistory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedicalHistoryMapper {

    MedicalHistoryResponseDto toDto(MedicalHistory entity);

    MedicalHistory toEntity(MedicalHistoryRequestDto dto);

    void updateEntity(@MappingTarget MedicalHistory entity, MedicalHistoryRequestDto dto);
}

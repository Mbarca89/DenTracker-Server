package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.request.patient.PatientRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.PatientResponseDto;
import com.mbarca89.DenTracker.entity.patient.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    // Convierte entidad a DTO de respuesta
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
    PatientResponseDto toDto(Patient patient);

    // Convierte lista de entidades a lista de DTOs
    List<PatientResponseDto> toDtoList(List<Patient> patients);

    // Convierte DTO de request a entidad nueva
    Patient toEntity(PatientRequestDto dto);

    // Aplica cambios desde DTO a entidad existente
    void updateEntity(@MappingTarget Patient patient, PatientRequestDto dto);
}


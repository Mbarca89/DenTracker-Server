package com.mbarca89.DenTracker.mapper;

import com.mbarca89.DenTracker.dto.request.PatientRequestDto;
import com.mbarca89.DenTracker.dto.response.PatientResponseDto;
import com.mbarca89.DenTracker.entity.client.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    Patient toPatient(PatientRequestDto patientRequestDto);
    PatientResponseDto toPatientResponseDto(Patient patient);

}
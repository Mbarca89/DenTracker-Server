package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.request.patient.PatientRequestDto;
import com.mbarca89.DenTracker.dto.response.client.CreatedByUserDto;
import com.mbarca89.DenTracker.dto.response.patient.PatientResponseDto;
import com.mbarca89.DenTracker.entity.patient.Patient;
import com.mbarca89.DenTracker.repository.client.ClientUserRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PatientMapper {

    @Autowired
    protected ClientUserRepository clientUserRepository;

    public abstract PatientResponseDto toDto(Patient patient);

    public abstract List<PatientResponseDto> toDtoList(List<Patient> patients);

    public abstract Patient toEntity(PatientRequestDto dto);

    public abstract void updateEntity(@MappingTarget Patient patient, PatientRequestDto dto);

    @AfterMapping
    protected void enrichWithCreatedBy(Patient patient, @MappingTarget PatientResponseDto dto) {
        if (patient.getCreatedByUserId() != null) {
            clientUserRepository.findById(patient.getCreatedByUserId()).ifPresent(user -> {
                CreatedByUserDto createdBy = new CreatedByUserDto();
                createdBy.setId(user.getId());
                createdBy.setName(user.getName());
                createdBy.setRole(user.getRole().getDisplayName());
                dto.setCreatedBy(createdBy);
            });
        }
    }
}



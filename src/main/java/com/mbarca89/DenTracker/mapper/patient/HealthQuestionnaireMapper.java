package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.request.patient.HealthQuestionnaireRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.HealthQuestionnaireResponseDto;
import com.mbarca89.DenTracker.entity.patient.HealthQuestionnaire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HealthQuestionnaireMapper {

    @Mapping(source = "patient.id", target = "patientId")
    HealthQuestionnaireResponseDto toDto(HealthQuestionnaire entity);

    void updateEntity(@MappingTarget HealthQuestionnaire entity, HealthQuestionnaireRequestDto dto);
}

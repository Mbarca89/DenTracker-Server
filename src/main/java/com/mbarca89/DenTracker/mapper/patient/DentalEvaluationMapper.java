package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.response.patient.DentalEvaluationResponseDto;
import com.mbarca89.DenTracker.entity.patient.DentalEvaluation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DentalEvaluationMapper {
    DentalEvaluationResponseDto toDto(DentalEvaluation entity);
}

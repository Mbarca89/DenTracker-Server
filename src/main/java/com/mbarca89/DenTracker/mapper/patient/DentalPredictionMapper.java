package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.response.patient.DentalPredictionResponseDto;
import com.mbarca89.DenTracker.entity.patient.DentalPrediction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DentalPredictionMapper {
    DentalPredictionResponseDto toDto(DentalPrediction entity);
}

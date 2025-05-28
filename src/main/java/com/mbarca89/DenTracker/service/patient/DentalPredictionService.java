package com.mbarca89.DenTracker.service.patient;

import com.mbarca89.DenTracker.dto.response.patient.DentalPredictionResponseDto;
import com.mbarca89.DenTracker.dto.request.patient.DentalPredictionRequestDto;

public interface DentalPredictionService {
    DentalPredictionResponseDto getByPatientId(Long patientId);
    DentalPredictionResponseDto updateDentalPrediction(Long patientId, DentalPredictionRequestDto dto);
}

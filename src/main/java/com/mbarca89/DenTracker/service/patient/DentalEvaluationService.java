package com.mbarca89.DenTracker.service.patient;

import com.mbarca89.DenTracker.dto.request.patient.DentalEvaluationRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.DentalEvaluationResponseDto;

public interface DentalEvaluationService {
    DentalEvaluationResponseDto getByPatientId(Long patientId);
    DentalEvaluationResponseDto updateDentalEvaluation(Long patientId, DentalEvaluationRequestDto dto);
}

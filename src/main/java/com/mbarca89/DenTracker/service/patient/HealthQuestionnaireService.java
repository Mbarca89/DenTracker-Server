package com.mbarca89.DenTracker.service.patient;

import com.mbarca89.DenTracker.dto.request.patient.HealthQuestionnaireRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.HealthQuestionnaireResponseDto;

public interface HealthQuestionnaireService {
    HealthQuestionnaireResponseDto getByPatientId(Long patientId);
    void update(Long patientId, HealthQuestionnaireRequestDto dto);
}

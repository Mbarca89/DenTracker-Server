package com.mbarca89.DenTracker.service.patient;

import com.mbarca89.DenTracker.dto.request.patient.MedicalHistoryRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.MedicalHistoryResponseDto;

public interface MedicalHistoryService {
    MedicalHistoryResponseDto getByPatientId(Long patientId);

    void update(Long patientId, MedicalHistoryRequestDto dto);
}

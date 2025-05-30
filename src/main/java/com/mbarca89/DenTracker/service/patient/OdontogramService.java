package com.mbarca89.DenTracker.service.patient;

import com.mbarca89.DenTracker.dto.request.patient.OdontogramRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.OdontogramResponseDto;

import java.util.List;

public interface OdontogramService {
    void createOdontogram(Long patientId, OdontogramRequestDto dto);
    OdontogramResponseDto getLastOdontogram(Long patientId);
    OdontogramResponseDto getOdontogramById(Long id);
    List<OdontogramResponseDto> getOdontogramsByPatientId(Long patientId);
}
package com.mbarca89.DenTracker.service.patient;

import com.mbarca89.DenTracker.dto.request.patient.SurgicalProtocolRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.SurgicalProtocolListResponseDto;
import com.mbarca89.DenTracker.dto.response.patient.SurgicalProtocolResponseDto;

import java.util.List;

public interface SurgicalProtocolService {
    SurgicalProtocolResponseDto createSurgicalProtocol(SurgicalProtocolRequestDto dto, Long patientId);
    List<SurgicalProtocolListResponseDto> getAllByPatientId(Long patientId);
    SurgicalProtocolResponseDto getById(Long id);
}

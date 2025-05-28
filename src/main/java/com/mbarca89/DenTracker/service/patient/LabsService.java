package com.mbarca89.DenTracker.service.patient;

import com.mbarca89.DenTracker.dto.request.patient.LabsObservationsRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.LabsResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface LabsService {
    void uploadLabFiles(Long patientId, String labType, MultipartFile[] files);
    LabsResponseDto getAllLabsByPatientId(Long patientId);
    void updateLabsObservations(Long patientId, LabsObservationsRequestDto dto);
}

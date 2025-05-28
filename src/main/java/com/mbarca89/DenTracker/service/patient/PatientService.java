package com.mbarca89.DenTracker.service.patient;

import com.mbarca89.DenTracker.dto.request.patient.PatientRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.PatientResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PatientService {
    PatientResponseDto createPatient(PatientRequestDto dto, HttpServletRequest request);
    List<PatientResponseDto> getAllPatients(HttpServletRequest request);
    PatientResponseDto getPatientById(Long id, HttpServletRequest request);
    List<PatientResponseDto> searchByName(String name, HttpServletRequest request);
    void updatePatient(Long id, PatientRequestDto dto, HttpServletRequest request);
    void deletePatient(Long id, HttpServletRequest request);
    void hardDeletePatient(Long id, HttpServletRequest request);
    void transferPatient(Long patientId, Long targetUserId, HttpServletRequest request);
    List<PatientResponseDto> getPatientsByUser(HttpServletRequest request);

}


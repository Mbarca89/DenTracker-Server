package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.request.patient.MedicalHistoryRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.MedicalHistoryResponseDto;
import com.mbarca89.DenTracker.entity.patient.MedicalHistory;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.MedicalHistoryMapper;
import com.mbarca89.DenTracker.repository.patient.MedicalHistoryRepository;
import com.mbarca89.DenTracker.service.patient.MedicalHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;

    @Transactional(readOnly = true)
    @Override
    public MedicalHistoryResponseDto getByPatientId(Long patientId) {
        MedicalHistory medicalHistory = medicalHistoryRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la historia médica para el paciente."));
        return medicalHistoryMapper.toDto(medicalHistory);
    }

    @Transactional
    @Override
    public void update(Long patientId, MedicalHistoryRequestDto dto) {
        MedicalHistory medicalHistory = medicalHistoryRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la historia médica para el paciente."));
        medicalHistoryMapper.updateEntity(medicalHistory, dto);
        medicalHistoryRepository.save(medicalHistory);
    }
}

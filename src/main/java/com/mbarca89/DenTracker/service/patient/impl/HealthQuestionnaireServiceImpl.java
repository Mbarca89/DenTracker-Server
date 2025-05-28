package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.request.patient.HealthQuestionnaireRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.HealthQuestionnaireResponseDto;
import com.mbarca89.DenTracker.entity.patient.HealthQuestionnaire;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.HealthQuestionnaireMapper;
import com.mbarca89.DenTracker.repository.patient.HealthQuestionnaireRepository;
import com.mbarca89.DenTracker.service.patient.HealthQuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HealthQuestionnaireServiceImpl implements HealthQuestionnaireService {

    private final HealthQuestionnaireRepository repository;
    private final HealthQuestionnaireMapper mapper;

    @Override
    public HealthQuestionnaireResponseDto getByPatientId(Long patientId) {
        HealthQuestionnaire entity = repository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Cuestionario de salud no encontrado"));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public void update(Long patientId, HealthQuestionnaireRequestDto dto) {
        HealthQuestionnaire entity = repository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Cuestionario de salud no encontrado"));

        mapper.updateEntity(entity, dto);
        repository.save(entity);
    }
}

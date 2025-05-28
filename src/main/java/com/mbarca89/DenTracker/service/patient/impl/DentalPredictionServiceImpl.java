package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.request.patient.DentalPredictionRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.DentalPredictionResponseDto;
import com.mbarca89.DenTracker.entity.patient.DentalPrediction;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.DentalPredictionMapper;
import com.mbarca89.DenTracker.repository.patient.DentalPredictionRepository;
import com.mbarca89.DenTracker.service.patient.DentalPredictionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DentalPredictionServiceImpl implements DentalPredictionService {

    private final DentalPredictionRepository dentalPredictionRepository;
    private final DentalPredictionMapper dentalPredictionMapper;

    @Override
    public DentalPredictionResponseDto getByPatientId(Long patientId) {
        DentalPrediction prediction = dentalPredictionRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró predicción dental para este paciente."));
        return dentalPredictionMapper.toDto(prediction);
    }

    @Transactional
    @Override
    public DentalPredictionResponseDto updateDentalPrediction(Long patientId, DentalPredictionRequestDto dto) {
        DentalPrediction prediction = dentalPredictionRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró predicción dental para este paciente."));

        prediction.setTop(dto.getTop());
        prediction.setBottom(dto.getBottom());

        dentalPredictionRepository.save(prediction);
        return dentalPredictionMapper.toDto(prediction);
    }
}

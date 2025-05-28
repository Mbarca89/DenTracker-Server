package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.request.patient.DentalEvaluationRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.DentalEvaluationResponseDto;
import com.mbarca89.DenTracker.entity.patient.DentalEvaluation;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.DentalEvaluationMapper;
import com.mbarca89.DenTracker.repository.patient.DentalEvaluationRepository;
import com.mbarca89.DenTracker.service.patient.DentalEvaluationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DentalEvaluationServiceImpl implements DentalEvaluationService {

    private final DentalEvaluationRepository dentalEvaluationRepository;
    private final DentalEvaluationMapper dentalEvaluationMapper;

    @Override
    public DentalEvaluationResponseDto getByPatientId(Long patientId) {
        DentalEvaluation evaluation = dentalEvaluationRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró evaluación dental para este paciente."));
        return dentalEvaluationMapper.toDto(evaluation);
    }

    @Transactional
    @Override
    public DentalEvaluationResponseDto updateDentalEvaluation(Long patientId, DentalEvaluationRequestDto dto) {
        DentalEvaluation evaluation = dentalEvaluationRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró evaluación dental para este paciente."));

        evaluation.setBrush(dto.isBrush());
        evaluation.setBrushFrequency(dto.getBrushFrequency());
        evaluation.setFloss(dto.isFloss());
        evaluation.setFlossFrequency(dto.getFlossFrequency());
        evaluation.setInterdentalBrush(dto.isInterdentalBrush());
        evaluation.setInterdentalBrushFrequency(dto.getInterdentalBrushFrequency());
        evaluation.setBiotype(dto.getBiotype());
        evaluation.setSmile(dto.getSmile());
        evaluation.setVerticalLoss(dto.isVerticalLoss());
        evaluation.setJawPosition(dto.isJawPosition());
        evaluation.setDispersion(dto.isDispersion());
        evaluation.setWear(dto.isWear());
        evaluation.setWearType(dto.getWearType());
        evaluation.setInternalExam(dto.getInternalExam());
        evaluation.setExternalExam(dto.getExternalExam());

        dentalEvaluationRepository.save(evaluation);
        return dentalEvaluationMapper.toDto(evaluation);
    }
}

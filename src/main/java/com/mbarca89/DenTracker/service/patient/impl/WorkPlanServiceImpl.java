package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.request.patient.WorkPlanRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.WorkPlanResponseDto;
import com.mbarca89.DenTracker.entity.patient.Patient;
import com.mbarca89.DenTracker.entity.patient.WorkPlan;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.WorkPlanMapper;
import com.mbarca89.DenTracker.repository.patient.PatientRepository;
import com.mbarca89.DenTracker.repository.patient.WorkPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkPlanServiceImpl {

    private final WorkPlanRepository workPlanRepository;
    private final PatientRepository patientRepository;
    private final WorkPlanMapper workPlanMapper;

    public WorkPlanResponseDto createWorkPlan(Long patientId, WorkPlanRequestDto dto) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        WorkPlan workPlan = workPlanMapper.toEntity(dto);
        workPlan.setPatient(patient);
        WorkPlan saved = workPlanRepository.save(workPlan);

        return workPlanMapper.toDto(saved);
    }

    public List<WorkPlanResponseDto> getWorkPlansByPatientId(Long patientId) {
        List<WorkPlan> plans = workPlanRepository.findByPatientId(patientId);
        return workPlanMapper.toDtoList(plans);
    }

    public void addStage(Long id, String stage) {
        WorkPlan workPlan = workPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan de trabajo no encontrado"));

        List<String> stages = new ArrayList<>();
        if (workPlan.getStages() != null) {
            stages.addAll(workPlan.getStages());
        }
        stages.add(stage);
        workPlan.setStages(stages);

        workPlanRepository.save(workPlan);
    }

    public void updateStage(Long id, List<String> newStages) {
        WorkPlan workPlan = workPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan de trabajo no encontrado"));

        workPlan.setStages(newStages);
        workPlanRepository.save(workPlan);
    }

    public void closeWorkPlan(Long id, String newStatus) {
        WorkPlan workPlan = workPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan de trabajo no encontrado"));

        workPlan.setEndDate(LocalDateTime.now());
        workPlan.setStatus(newStatus);
        workPlanRepository.save(workPlan);
    }
}

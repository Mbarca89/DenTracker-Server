package com.mbarca89.DenTracker.service.patient;

import com.mbarca89.DenTracker.dto.request.patient.WorkPlanRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.WorkPlanResponseDto;

import java.util.List;

public interface WorkPlanService {
    WorkPlanResponseDto createWorkPlan(Long patientId, WorkPlanRequestDto dto);
    List<WorkPlanResponseDto> getWorkPlansByPatientId(Long patientId);
   void addStage(Long id, String stage);
    void updateStage(Long id, List<String> newStages);
    void closeWorkPlan(Long id, String newStatus);
}

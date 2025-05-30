package com.mbarca89.DenTracker.repository.patient;

import com.mbarca89.DenTracker.entity.patient.WorkPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WorkPlanRepository extends JpaRepository<WorkPlan, Long> {
    List<WorkPlan> findByPatientId(Long patientId);
}

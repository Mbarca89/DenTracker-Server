package com.mbarca89.DenTracker.repository.patient;

import com.mbarca89.DenTracker.entity.patient.DentalEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DentalEvaluationRepository extends JpaRepository<DentalEvaluation, Long> {
    Optional<DentalEvaluation> findByPatientId(Long patientId);
}

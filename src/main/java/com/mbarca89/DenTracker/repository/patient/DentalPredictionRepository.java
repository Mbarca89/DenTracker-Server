package com.mbarca89.DenTracker.repository.patient;

import com.mbarca89.DenTracker.entity.patient.DentalPrediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DentalPredictionRepository extends JpaRepository<DentalPrediction, Long> {

    Optional<DentalPrediction> findByPatientId(Long patientId);
}

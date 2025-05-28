package com.mbarca89.DenTracker.repository.patient;

import com.mbarca89.DenTracker.entity.patient.Labs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabsRepository extends JpaRepository<Labs, Long> {
    Optional<Labs> findByPatientId(Long patientId);
}

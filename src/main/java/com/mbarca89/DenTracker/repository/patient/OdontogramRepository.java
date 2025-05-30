package com.mbarca89.DenTracker.repository.patient;

import com.mbarca89.DenTracker.entity.patient.Odontogram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OdontogramRepository extends JpaRepository<Odontogram, Long> {
    Optional<Odontogram> findTopByPatientIdOrderByOdontogramDateDesc(Long patientId);
    Optional<List<Odontogram>> findByPatientId(Long patientId);
}
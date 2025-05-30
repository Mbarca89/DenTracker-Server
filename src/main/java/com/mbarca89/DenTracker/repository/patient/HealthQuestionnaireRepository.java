package com.mbarca89.DenTracker.repository.patient;

import com.mbarca89.DenTracker.entity.patient.HealthQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface HealthQuestionnaireRepository extends JpaRepository<HealthQuestionnaire, Long> {
    Optional<HealthQuestionnaire> findByPatientId(Long patientId);
}

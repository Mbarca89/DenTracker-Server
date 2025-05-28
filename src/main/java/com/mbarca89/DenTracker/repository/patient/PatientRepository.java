package com.mbarca89.DenTracker.repository.patient;

import com.mbarca89.DenTracker.entity.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByClientIdAndActiveTrue(Long clientId);

    Optional<Patient> findByIdAndClientIdAndActiveTrue(Long id, Long clientId);

    Optional<Patient> findByIdAndClientId(Long id, Long clientId);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%')) AND p.clientId = :clientId AND p.active = true")
    List<Patient> searchByNameAndClientId(String name, Long clientId);

    List<Patient> findByActiveFalseAndUpdatedAtBefore(LocalDateTime cutoff); // para el future cron
    List<Patient> findByClientIdAndCreatedByUserIdAndActiveTrue(Long clientId, Long createdByUserId);

}

package com.mbarca89.DenTracker.repository.patient;

import com.mbarca89.DenTracker.entity.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
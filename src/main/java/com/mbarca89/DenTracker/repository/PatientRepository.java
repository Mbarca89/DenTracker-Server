package com.mbarca89.DenTracker.repository;

import com.mbarca89.DenTracker.entity.client.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
package com.mbarca89.DenTracker.repository.patient;

import com.mbarca89.DenTracker.entity.patient.SurgicalProtocol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SurgicalProtocolRepository extends JpaRepository<SurgicalProtocol, Long> {

    List<SurgicalProtocol> findByPatientId(Long patientId);

}

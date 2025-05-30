package com.mbarca89.DenTracker.repository.patient;

import com.mbarca89.DenTracker.entity.patient.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    Optional<Gallery> findByPatientId(Long patientId);
}

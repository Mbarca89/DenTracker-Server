package com.mbarca89.DenTracker.repository.client;

import com.mbarca89.DenTracker.entity.client.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByClientId(Long clientId);
    Optional<Appointment> findByIdAndClientId(Long id, Long clientId);
}

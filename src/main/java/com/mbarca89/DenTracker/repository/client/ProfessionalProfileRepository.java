package com.mbarca89.DenTracker.repository.client;

import com.mbarca89.DenTracker.entity.client.ProfessionalProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfile, Long> {
    Optional<ProfessionalProfile> findByUserId(Long userId);
}

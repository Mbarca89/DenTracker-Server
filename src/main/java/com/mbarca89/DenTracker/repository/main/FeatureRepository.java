package com.mbarca89.DenTracker.repository.main;

import com.mbarca89.DenTracker.entity.main.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findByCode(String code);
}

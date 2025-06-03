package com.mbarca89.DenTracker.service.main.impl;

import com.mbarca89.DenTracker.entity.main.Feature;
import com.mbarca89.DenTracker.repository.main.FeatureRepository;
import com.mbarca89.DenTracker.service.main.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;

    @Override
    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

    @Override
    public Feature createFeature(Feature feature) {
        // Podés agregar validaciones, p.ej. que el code sea único, etc.
        return featureRepository.save(feature);
    }
}

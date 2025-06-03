package com.mbarca89.DenTracker.service.main.impl;

import com.mbarca89.DenTracker.entity.main.Feature;
import com.mbarca89.DenTracker.entity.main.Plan;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.repository.main.FeatureRepository;
import com.mbarca89.DenTracker.repository.main.PlanRepository;
import com.mbarca89.DenTracker.service.main.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final FeatureRepository featureRepository;

    @Override
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    @Override
    public Plan getPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public Plan saveOrUpdatePlan(Plan plan) {
        // Útil para crear o editar nombre/description/límites
        return planRepository.save(plan);
    }

    @Override
    @Transactional
    public Plan addFeatureToPlan(Long planId, String featureCode) {
        Plan plan = getPlanById(planId);
        Feature feature = featureRepository.findByCode(featureCode)
                .orElseThrow(() -> new ResourceNotFoundException("Feature no encontrado con code: " + featureCode));
        plan.getFeatures().add(feature);
        return planRepository.save(plan);
    }

    @Override
    @Transactional
    public Plan removeFeatureFromPlan(Long planId, String featureCode) {
        Plan plan = getPlanById(planId);
        Feature feature = featureRepository.findByCode(featureCode)
                .orElseThrow(() -> new ResourceNotFoundException("Feature no encontrado con code: " + featureCode));
        plan.getFeatures().remove(feature);
        return planRepository.save(plan);
    }

    @Override
    @Transactional
    public Plan setFeaturesForPlan(Long planId, Set<String> featureCodes) {
        Plan plan = getPlanById(planId);
        Set<Feature> nuevos = new HashSet<>();
        for (String code : featureCodes) {
            Feature f = featureRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException("Feature no encontrado con code: " + code));
            nuevos.add(f);
        }
        plan.setFeatures(nuevos);
        return planRepository.save(plan);
    }
}

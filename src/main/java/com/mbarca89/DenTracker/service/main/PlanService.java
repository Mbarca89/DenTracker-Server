package com.mbarca89.DenTracker.service.main;

import com.mbarca89.DenTracker.entity.main.Plan;

import java.util.List;
import java.util.Set;

public interface PlanService {
    List<Plan> getAllPlans();
    Plan getPlanById(Long id);
    Plan saveOrUpdatePlan(Plan plan);
    Plan addFeatureToPlan(Long planId, String featureCode);
    Plan removeFeatureFromPlan(Long planId, String featureCode);
    Plan setFeaturesForPlan(Long planId, Set<String> featureCodes);
}

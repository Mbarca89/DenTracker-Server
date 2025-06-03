package com.mbarca89.DenTracker.service.main;

public interface PlanFeatureCheckerService {
    boolean hasFeature(Long clientUserId, String featureCode);
}

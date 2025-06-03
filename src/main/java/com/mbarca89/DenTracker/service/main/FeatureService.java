package com.mbarca89.DenTracker.service.main;

import com.mbarca89.DenTracker.entity.main.Feature;
import java.util.List;

public interface FeatureService {
    List<Feature> getAllFeatures();
    Feature createFeature(Feature feature);
}

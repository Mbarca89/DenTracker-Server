package com.mbarca89.DenTracker.controller.mainController;

import com.mbarca89.DenTracker.dto.request.main.PlanWithFeaturesRequestDto;
import com.mbarca89.DenTracker.dto.response.main.PlanWithFeaturesResponseDto;
import com.mbarca89.DenTracker.entity.main.Plan;
import com.mbarca89.DenTracker.mapper.main.PlanMapper;
import com.mbarca89.DenTracker.service.main.FeatureService;
import com.mbarca89.DenTracker.service.main.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final FeatureService featureService;
    private final PlanMapper planMapper;

    @GetMapping("/{planId}")
    public PlanWithFeaturesResponseDto getPlanWithFeatures(@PathVariable Long planId) {
        Plan plan = planService.getPlanById(planId);
        return planMapper.toPlanWithFeaturesDto(plan);
    }

    @PostMapping("/features")
    public PlanWithFeaturesResponseDto updatePlanFeatures(@RequestBody PlanWithFeaturesRequestDto dto) {
        Plan updated = planService.setFeaturesForPlan(dto.getPlanId(), dto.getFeatureCodes());
        return planMapper.toPlanWithFeaturesDto(updated);
    }
}

package com.mbarca89.DenTracker.repository.main;

import com.mbarca89.DenTracker.entity.main.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Plan findByName(String name);
}

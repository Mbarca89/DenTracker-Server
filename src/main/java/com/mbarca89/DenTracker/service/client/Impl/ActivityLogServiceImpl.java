package com.mbarca89.DenTracker.service.client.Impl;

import com.mbarca89.DenTracker.entity.client.ActivityLog;
import com.mbarca89.DenTracker.repository.client.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl {

    private final ActivityLogRepository activityLogRepository;

    public List<ActivityLog> getByPatientId(Long patientId) {
        return activityLogRepository.findByPatientIdOrderByTimestampDesc(patientId);
    }
}

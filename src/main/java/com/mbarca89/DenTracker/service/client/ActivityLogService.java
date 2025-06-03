package com.mbarca89.DenTracker.service.client;

import com.mbarca89.DenTracker.entity.client.ActivityLog;

import java.util.List;

public interface ActivityLogService {
    List<ActivityLog> getByPatientId(Long patientId);
}

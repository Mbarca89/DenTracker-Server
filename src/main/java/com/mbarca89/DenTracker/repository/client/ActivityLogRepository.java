package com.mbarca89.DenTracker.repository.client;

import com.mbarca89.DenTracker.entity.client.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByPatientIdOrderByTimestampDesc(Long patientId);
}

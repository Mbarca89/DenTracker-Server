package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.response.client.ActivityLogResponseDto;
import com.mbarca89.DenTracker.mapper.client.ActivityLogMapper;
import com.mbarca89.DenTracker.service.client.Impl.ActivityLogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-logs")
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogServiceImpl activityLogService;
    private final ActivityLogMapper activityLogMapper;

    @GetMapping("/{patientId}")
    public List<ActivityLogResponseDto> getActivityLogs(@PathVariable Long patientId) {
        return activityLogMapper.toResponseDtoList(activityLogService.getByPatientId(patientId));
    }
}

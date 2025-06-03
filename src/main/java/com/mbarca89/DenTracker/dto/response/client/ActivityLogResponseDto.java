package com.mbarca89.DenTracker.dto.response.client;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityLogResponseDto {
    private Long id;
    private String activity;
    private String username;
    private LocalDateTime timestamp;
}

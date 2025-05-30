package com.mbarca89.DenTracker.dto.response.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlanListResponseDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}

package com.mbarca89.DenTracker.dto.request.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlanRequestDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private String observations;
    private List<String> stages;
}

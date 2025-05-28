package com.mbarca89.DenTracker.dto.response.patient;

import lombok.Data;

import java.util.Map;

@Data
public class DentalPredictionResponseDto {
    private Long id;
    private Map<String, String> top;
    private Map<String, String> bottom;
}

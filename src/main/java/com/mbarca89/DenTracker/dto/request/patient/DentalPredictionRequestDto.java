package com.mbarca89.DenTracker.dto.request.patient;

import lombok.Data;

import java.util.Map;

@Data
public class DentalPredictionRequestDto {
    private Map<String, String> top;
    private Map<String, String> bottom;
}

package com.mbarca89.DenTracker.dto.response.patient;

import lombok.Data;

@Data
public class DentalEvaluationResponseDto {
    private Long id;
    private boolean brush;
    private String brushFrequency;
    private boolean floss;
    private String flossFrequency;
    private boolean interdentalBrush;
    private String interdentalBrushFrequency;
    private String biotype;
    private String smile;
    private boolean verticalLoss;
    private boolean jawPosition;
    private boolean dispersion;
    private boolean wear;
    private String wearType;
    private String internalExam;
    private String externalExam;
}

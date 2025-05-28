package com.mbarca89.DenTracker.dto.request.patient;

import lombok.Data;

@Data
public class MedicalHistoryRequestDto {
    private String parents;
    private String siblings;
    private String children;
    private String actualDiseaseHistory;
    private String pathologicalHistory;
    private String traumaHistory;
    private String surgeries;
    private String medication;
    private String allergies;
    private boolean alcohol;
    private boolean tobacco;
    private boolean drugs;
    private String drugsDetail;
}

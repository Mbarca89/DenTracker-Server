package com.mbarca89.DenTracker.dto.request.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabsObservationsRequestDto {
    private String hemogramObservations;
    private String glycemiaObservations;
    private String hemoglobinObservations;
    private String uraemiaObservations;
    private String coagulogramObservations;
    private String urineObservations;
    private String antitetanusObservations;
    private String ctxObservations;
}

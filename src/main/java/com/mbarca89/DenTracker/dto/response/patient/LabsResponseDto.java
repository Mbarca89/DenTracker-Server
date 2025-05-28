package com.mbarca89.DenTracker.dto.response.patient;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LabsResponseDto {
    private Long id;

    private List<String> hemogram;
    private String hemogramObservations;

    private List<String> glycemia;
    private String glycemiaObservations;

    private List<String> hemoglobin;
    private String hemoglobinObservations;

    private List<String> uraemia;
    private String uraemiaObservations;

    private List<String> coagulagram;
    private String coagulogramObservations;

    private List<String> urine;
    private String urineObservations;

    private List<String> antitetanus;
    private String antitetanusObservations;

    private List<String> ctx;
    private String ctxObservations;
}

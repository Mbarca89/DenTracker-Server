package com.mbarca89.DenTracker.dto.response.patient;

import java.time.LocalDate;
import java.util.List;

public class OdontogramResponseDto {
    private Long id;
    private String odontogramJson;
    private List<TreatmentDto> treatments;
    private LocalDate odontogramDate;
}
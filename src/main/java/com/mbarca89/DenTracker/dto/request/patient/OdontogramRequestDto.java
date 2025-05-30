package com.mbarca89.DenTracker.dto.request.patient;

import com.mbarca89.DenTracker.dto.response.patient.TreatmentDto;

import java.util.List;

public class OdontogramRequestDto {
    private String odontogramJson;
    private List<TreatmentDto> treatments;
}
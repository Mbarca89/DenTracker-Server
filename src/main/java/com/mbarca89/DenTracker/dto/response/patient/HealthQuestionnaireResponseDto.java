package com.mbarca89.DenTracker.dto.response.patient;

import com.mbarca89.DenTracker.dto.request.patient.HealthQuestionnaireRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HealthQuestionnaireResponseDto extends HealthQuestionnaireRequestDto {
    private Long id;
    private Long patientId;
}

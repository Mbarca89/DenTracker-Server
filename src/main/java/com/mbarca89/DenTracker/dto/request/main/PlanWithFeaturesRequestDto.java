package com.mbarca89.DenTracker.dto.request.main;

import lombok.Data;
import java.util.Set;

/**
 * Este DTO se usa para enviar desde el front la lista completa de features
 * que se quieren asignar a un plan determinado.
 */
@Data
public class PlanWithFeaturesRequestDto {
    private Long planId;
    private Set<String> featureCodes;
}

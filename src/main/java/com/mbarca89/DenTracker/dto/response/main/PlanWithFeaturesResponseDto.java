package com.mbarca89.DenTracker.dto.response.main;

import lombok.Data;
import java.util.Set;

/**
 * Representa un Plan junto con la lista de códigos de features que tiene asignados.
 */
@Data
public class PlanWithFeaturesResponseDto {
    private Long id;
    private String name;
    private String description;
    private int maxUsers;
    private int maxPatients;
    private long storageLimitMb;
    private Set<String> featureCodes;
}

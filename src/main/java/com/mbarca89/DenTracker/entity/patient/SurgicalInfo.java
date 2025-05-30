package com.mbarca89.DenTracker.entity.patient;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SurgicalInfo {
    private String anesthesiaType;
    private String bleeding;
    private String duration;
    private String difficulties;
    private String complications;
}

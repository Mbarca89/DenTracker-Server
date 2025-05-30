package com.mbarca89.DenTracker.entity.patient;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Treatment {
    private String name;
    private String status;
}
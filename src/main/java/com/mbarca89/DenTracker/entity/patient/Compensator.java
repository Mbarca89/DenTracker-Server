package com.mbarca89.DenTracker.entity.patient;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Compensator {
    private String brand;
    private String type;
    private String size;
}

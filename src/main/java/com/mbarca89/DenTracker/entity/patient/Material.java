package com.mbarca89.DenTracker.entity.patient;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Material {
    private String brand;
    private String type;
    private String use;
}

package com.mbarca89.DenTracker.entity.patient;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Implant {
    private String brand;
    private String reference;
    private String diameter;
    private String length;
    private String position;
}

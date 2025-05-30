package com.mbarca89.DenTracker.dto.request.patient;

import com.mbarca89.DenTracker.entity.patient.Compensator;
import com.mbarca89.DenTracker.entity.patient.Implant;
import com.mbarca89.DenTracker.entity.patient.Material;
import com.mbarca89.DenTracker.entity.patient.SurgicalInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SurgicalProtocolRequestDto {
    private Long patientId;
    private List<Implant> implants;
    private List<Compensator> compensators;
    private List<Material> materials;
    private SurgicalInfo surgicalInfo;
    private String observations;
}

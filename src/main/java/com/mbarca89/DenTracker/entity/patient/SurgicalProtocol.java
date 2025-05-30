package com.mbarca89.DenTracker.entity.patient;

import com.mbarca89.DenTracker.entity.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "surgical_protocols")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurgicalProtocol extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private String surgicalDiagnosis;
    private String surgicalPlan;
    private String boneRegeneration;
    private String surgicalProcedure;
    private String observations;

    @ElementCollection
    @CollectionTable(name = "implants", joinColumns = @JoinColumn(name = "surgical_protocol_id"))
    private List<Implant> implants;

    @ElementCollection
    @CollectionTable(name = "compensators", joinColumns = @JoinColumn(name = "surgical_protocol_id"))
    private List<Compensator> compensators;

    @ElementCollection
    @CollectionTable(name = "materials", joinColumns = @JoinColumn(name = "surgical_protocol_id"))
    private List<Material> materials;

    @Embedded
    private SurgicalInfo surgicalInfo;
}

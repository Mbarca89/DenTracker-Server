package com.mbarca89.DenTracker.entity.patient;

import com.mbarca89.DenTracker.entity.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medical_history")
@Getter
@Setter
@NoArgsConstructor
public class MedicalHistory extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private String parents;
    private String siblings;
    private String children;
    private String actualDiseaseHistory;
    private String pathologicalHistory;
    private String traumaHistory;
    private String surgeries;
    private String medication;
    private String allergies;

    private boolean alcohol;
    private boolean tobacco;
    private boolean drugs;
    private String drugsDetail;

    public MedicalHistory(Patient patient) {
        this.patient = patient;
    }
}

package com.mbarca89.DenTracker.entity.patient;

import com.mbarca89.DenTracker.entity.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dental_evaluations")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class DentalEvaluation extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private boolean brush;
    private String brushFrequency;
    private boolean floss;
    private String flossFrequency;
    private boolean interdentalBrush;
    private String interdentalBrushFrequency;
    private String biotype;
    private String smile;
    private boolean verticalLoss;
    private boolean jawPosition;
    private boolean dispersion;
    private boolean wear;
    private String wearType;
    private String internalExam;
    private String externalExam;

    public DentalEvaluation(Patient patient) {
        this.patient = patient;
    }
}

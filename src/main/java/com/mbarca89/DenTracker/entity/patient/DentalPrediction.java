package com.mbarca89.DenTracker.entity.patient;

import com.mbarca89.DenTracker.entity.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "dental_predictions")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class DentalPrediction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ElementCollection
    @CollectionTable(name = "top_dental_map", joinColumns = @JoinColumn(name = "dental_prediction_id"))
    @MapKeyColumn(name = "teeth_number")
    @Column(name = "condition")
    private Map<String, String> top;

    @ElementCollection
    @CollectionTable(name = "bottom_dental_map", joinColumns = @JoinColumn(name = "dental_prediction_id"))
    @MapKeyColumn(name = "teeth_number")
    @Column(name = "condition")
    private Map<String, String> bottom;

    public DentalPrediction(Patient patient) {
        this.patient = patient;
    }
}

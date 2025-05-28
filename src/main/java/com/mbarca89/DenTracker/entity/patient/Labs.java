package com.mbarca89.DenTracker.entity.patient;

import com.mbarca89.DenTracker.entity.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "labs")
@Getter
@Setter
@NoArgsConstructor
public class Labs extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ElementCollection
    @CollectionTable(name = "labs_hemogram_files", joinColumns = @JoinColumn(name = "labs_id"))
    @Column(name = "file_path")
    private List<String> hemogram;

    private String hemogramObservations;

    @ElementCollection
    @CollectionTable(name = "labs_glycemia_files", joinColumns = @JoinColumn(name = "labs_id"))
    @Column(name = "file_path")
    private List<String> glycemia;

    private String glycemiaObservations;

    @ElementCollection
    @CollectionTable(name = "labs_hemoglobin_files", joinColumns = @JoinColumn(name = "labs_id"))
    @Column(name = "file_path")
    private List<String> hemoglobin;

    private String hemoglobinObservations;

    @ElementCollection
    @CollectionTable(name = "labs_uraemia_files", joinColumns = @JoinColumn(name = "labs_id"))
    @Column(name = "file_path")
    private List<String> uraemia;

    private String uraemiaObservations;

    @ElementCollection
    @CollectionTable(name = "labs_coagulagram_files", joinColumns = @JoinColumn(name = "labs_id"))
    @Column(name = "file_path")
    private List<String> coagulagram;

    private String coagulogramObservations;

    @ElementCollection
    @CollectionTable(name = "labs_urine_files", joinColumns = @JoinColumn(name = "labs_id"))
    @Column(name = "file_path")
    private List<String> urine;

    private String urineObservations;

    @ElementCollection
    @CollectionTable(name = "labs_antitetanus_files", joinColumns = @JoinColumn(name = "labs_id"))
    @Column(name = "file_path")
    private List<String> antitetanus;

    private String antitetanusObservations;

    @ElementCollection
    @CollectionTable(name = "labs_ctx_files", joinColumns = @JoinColumn(name = "labs_id"))
    @Column(name = "file_path")
    private List<String> ctx;

    private String ctxObservations;

    public Labs(Patient patient) {
        this.patient = patient;
    }
}

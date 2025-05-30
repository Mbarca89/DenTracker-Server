package com.mbarca89.DenTracker.entity.patient;

import com.mbarca89.DenTracker.entity.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "odontograms")
@Getter
@Setter
@NoArgsConstructor
public class Odontogram extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String odontogramJson;

    @ElementCollection
    @CollectionTable(name = "odontogram_treatments", joinColumns = @JoinColumn(name = "odontogram_id"))
    private List<Treatment> treatments;

    private LocalDateTime odontogramDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
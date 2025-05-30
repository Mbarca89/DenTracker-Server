package com.mbarca89.DenTracker.entity.patient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "work_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "observations")
    private String observations;

    @ElementCollection
    @CollectionTable(name = "work_plan_stages", joinColumns = @JoinColumn(name = "work_plan_id"))
    @Column(name = "stage")
    private List<String> stages;
}


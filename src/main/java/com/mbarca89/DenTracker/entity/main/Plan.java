package com.mbarca89.DenTracker.entity.main;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plans")
@Getter
@Setter
@NoArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private int maxUsers;
    private int maxPatients;
    private long storageLimitMb;

    private boolean enableAgenda;
    private boolean enablePatientHistory;
    private boolean enableFileUpload;
    private boolean enableHdImages;
    private boolean enableTreatmentPlan;
    private boolean enableSurgicalProtocol;
    private boolean enableOdontogram;
    private boolean enableOdontogramVersions;
    private boolean enableWhatsApp;
    private boolean enableDataExport;
    private boolean enableAuditLog;
    private boolean enableNotifications;
}
package com.mbarca89.DenTracker.entity.main;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    /**
     * En lugar de todos los booleans, tenemos un Set<Feature>.
     * Esto crea automáticamente la tabla intermedia plan_features(plan_id, feature_id).
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "plan_features",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private Set<Feature> features = new HashSet<>();
}

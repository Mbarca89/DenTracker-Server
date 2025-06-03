package com.mbarca89.DenTracker.entity.client;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "professional_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ClientUser user;

    private String fullName;

    @Lob
    private String profileImageBase64;

    private String registrationNumber;

    private String documentNumber;

    private String phone;

    private String email;
}

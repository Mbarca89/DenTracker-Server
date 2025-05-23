package com.mbarca89.DenTracker.entity.main;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    private Client client;

    @Column(nullable = false)
    private LocalDateTime expiration;

    private boolean used = false;
}

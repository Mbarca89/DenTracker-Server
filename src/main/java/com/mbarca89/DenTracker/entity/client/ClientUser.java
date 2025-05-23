package com.mbarca89.DenTracker.entity.client;

import com.mbarca89.DenTracker.entity.base.ClientUserRole;
import com.mbarca89.DenTracker.entity.main.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client_users")
@Getter
@Setter
@NoArgsConstructor
public class ClientUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientUserRole role;

    @Column(nullable = false)
    private String pin; // PIN de 4 dígitos (hasheado)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "active")
    private boolean active = true;
}

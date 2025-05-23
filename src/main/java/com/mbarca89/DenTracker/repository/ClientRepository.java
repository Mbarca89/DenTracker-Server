package com.mbarca89.DenTracker.repository;


import com.mbarca89.DenTracker.entity.main.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Client> findByVerificationToken(String token);

}

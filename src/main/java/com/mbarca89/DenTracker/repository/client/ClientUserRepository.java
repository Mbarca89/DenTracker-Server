package com.mbarca89.DenTracker.repository.client;

import com.mbarca89.DenTracker.entity.client.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ClientUserRepository extends JpaRepository<ClientUser, Long> {

    Optional<ClientUser> findByIdAndActiveTrue(Long id);
    Optional<ClientUser> findByName(String username);
    List<ClientUser> findAllByClientIdAndActiveTrue(Long clientId);
}

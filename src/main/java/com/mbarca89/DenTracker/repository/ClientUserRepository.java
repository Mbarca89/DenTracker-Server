package com.mbarca89.DenTracker.repository;

import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.main.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientUserRepository extends JpaRepository<ClientUser, Long> {

    Optional<ClientUser> findByIdAndActiveTrue(Long id);

    List<ClientUser> findAllByClientIdAndActiveTrue(Long clientId);
}

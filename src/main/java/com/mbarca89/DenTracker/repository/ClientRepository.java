package com.mbarca89.DenTracker.repository;


import com.mbarca89.DenTracker.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByUsername(String username);

}

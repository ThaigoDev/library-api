package com.thai.tec.librayapi.repositories;

import com.thai.tec.librayapi.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository  extends JpaRepository<Client, UUID> {
    Client findByClientId(String clientId);
}

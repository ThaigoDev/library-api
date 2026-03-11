package com.thai.tec.librayapi.repositories;

import com.thai.tec.librayapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);

    User findByUsername(String username);

    User findByEmail(String email);
}

package com.thai.tec.librayapi.repositories;

import com.thai.tec.librayapi.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}

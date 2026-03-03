package com.thai.tec.librayapi.repositories;

import com.thai.tec.librayapi.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    List<Author> findByNameAuthorAndNacionality(String nameAuthor, String nacionality);
    List<Author> findByNameAuthor(String nameAuthor);
    List<Author> findByNacionality(String nacionality);

     Optional<Author> findByNameAuthorAndDateOfBirthAndNacionality(String nameAuthor, LocalDate dateOfBirth, String nacionality);
}

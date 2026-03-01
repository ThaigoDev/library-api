package com.thai.tec.librayapi.repositories;

import com.thai.tec.librayapi.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    List<Author> findByNameAuthorAndNacionality(String nameAuthor, String nacionality);
    List<Author> findByNameAuthor(String nameAuthor);
    List<Author> findByNacionality(String nacionality);

}

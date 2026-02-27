package com.thai.tec.librayapi.service;

import com.thai.tec.librayapi.domain.dtos.RequestAuthorDTO;
import com.thai.tec.librayapi.domain.dtos.ResponseAuthorDTO;
import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.repositories.AuthorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    public ResponseAuthorDTO  saveAuthor(RequestAuthorDTO requestAuthorDTO) {
        Author author = requestAuthorDTO.mapToEntity();
        authorRepository.save(author);
       return new ResponseAuthorDTO(author.getNameAuthor(),author.getNameAuthor(),author.getNacionality());
    }

}

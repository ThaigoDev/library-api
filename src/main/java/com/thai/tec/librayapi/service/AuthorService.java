package com.thai.tec.librayapi.service;

import com.thai.tec.librayapi.domain.dtos.RequestAuthorDTO;
import com.thai.tec.librayapi.domain.dtos.ResponseAuthorDTO;
import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.repositories.AuthorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public ResponseAuthorDTO  saveAuthor(RequestAuthorDTO requestAuthorDTO) {

        Author author = requestAuthorDTO.mapToEntity();
        authorRepository.save(author);

       return new ResponseAuthorDTO(author.getId(), author.getNameAuthor(),author.getDateOfBirth(),author.getNacionality());
    }

    public List<ResponseAuthorDTO> getAllAuthors() {

          List<Author> authors = authorRepository.findAll();
          var authorsConverted =  authors.stream().map(author-> new ResponseAuthorDTO(author.getId(),author.getNameAuthor(),author.getDateOfBirth(),author.getNacionality())).toList();
          return  authorsConverted;
    }

    public   ResponseAuthorDTO  findByIdAuthor(String id) {
        UUID uuId =  UUID.fromString(id);
         Optional<Author> author =  authorRepository.findById(uuId);
         if(author.isPresent()) {
             return new ResponseAuthorDTO(author.get().getId(), author.get().getNameAuthor(), author.get().getDateOfBirth(),author.get().getNacionality());
         }else {
          return null;
         }
    }
}

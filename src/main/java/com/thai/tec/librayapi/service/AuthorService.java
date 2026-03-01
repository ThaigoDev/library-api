package com.thai.tec.librayapi.service;

import com.thai.tec.librayapi.domain.dtos.RequestAuthorDTO;
import com.thai.tec.librayapi.domain.dtos.ResponseAuthorDTO;
import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.repositories.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public ResponseAuthorDTO saveAuthor(RequestAuthorDTO requestAuthorDTO) {

        Author author = requestAuthorDTO.mapToEntity();
        authorRepository.save(author);

        return new ResponseAuthorDTO(author.getId(), author.getNameAuthor(), author.getDateOfBirth(), author.getNacionality());
    }

    public List<ResponseAuthorDTO> getAllAuthors() {

        List<Author> authors = authorRepository.findAll();
        var authorsConverted = authors.stream().map(author -> new ResponseAuthorDTO(author.getId(), author.getNameAuthor(), author.getDateOfBirth(), author.getNacionality())).toList();
        return authorsConverted;
    }

    public ResponseAuthorDTO findByIdAuthor(String id) {
        UUID uuId = UUID.fromString(id);
        Optional<Author> author = authorRepository.findById(uuId);
        if (author.isPresent()) {
            return new ResponseAuthorDTO(author.get().getId(), author.get().getNameAuthor(), author.get().getDateOfBirth(), author.get().getNacionality());
        } else {
            return null;
        }
    }

    public void deleteAuthorById(String id) {
        UUID uuid = UUID.fromString(id);
        Author authorExisted = authorRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found Author"));
        authorRepository.delete(authorExisted);

    }

    public List<ResponseAuthorDTO> search(String nameAuthor, String nacionality) {

        List<Author> listAuthors = new ArrayList<>();
        if (nameAuthor != null) {
            listAuthors = authorRepository.findByNameAuthor(nameAuthor);
        }else if (nacionality !=null) {
            listAuthors = authorRepository.findByNacionality(nameAuthor);
        } else if (nacionality!= null && nameAuthor!= null) {
                listAuthors = authorRepository.findByNameAuthorAndNacionality(nameAuthor, nacionality);
        } else {
             return authorRepository.findAll().stream().map(author -> new ResponseAuthorDTO(author.getId(), author.getNameAuthor(), author.getDateOfBirth(), author.getNacionality())).collect(Collectors.toList());
        }
        return listAuthors.stream().map(author -> new ResponseAuthorDTO(author.getId(), author.getNameAuthor(), author.getDateOfBirth(), author.getNacionality())).collect(Collectors.toList());


    }

    public void updateAuthorById(String id, RequestAuthorDTO requestAuthorDTO) {
         Author authorExisted =  authorRepository.findById(UUID.fromString(id)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

         authorExisted.setNameAuthor(requestAuthorDTO.nameAuthor());
         authorExisted.setNacionality(requestAuthorDTO.nacionality());
         authorExisted.setDateOfBirth(requestAuthorDTO.dateOfBirth());
         authorRepository.save(authorExisted);



    }
}

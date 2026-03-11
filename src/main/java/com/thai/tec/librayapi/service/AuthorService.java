package com.thai.tec.librayapi.service;

import com.thai.tec.librayapi.domain.dtos.authorDTO.RequestAuthorDTO;
import com.thai.tec.librayapi.domain.dtos.authorDTO.ResponseAuthorDTO;
import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.domain.entities.User;
import com.thai.tec.librayapi.exceptions.AuthorWithBookException;
import com.thai.tec.librayapi.mappers.AuthorMapper;
import com.thai.tec.librayapi.repositories.AuthorRepository;
import com.thai.tec.librayapi.repositories.BookRepository;
import com.thai.tec.librayapi.security.SecurityService;
import com.thai.tec.librayapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final BookRepository bookRepository;
    private final SecurityService securityService;
    private final AuthorMapper mapper;


    public ResponseAuthorDTO saveAuthor(RequestAuthorDTO requestAuthorDTO) {

             authorValidator.validator(requestAuthorDTO);
             var userLoged = securityService.gerUser();
            Author author = mapper.toEntity(requestAuthorDTO);
            author.setUser(userLoged);
            authorRepository.save(author);

            return mapper.toDTO(author);
    }

    public List<ResponseAuthorDTO> getAllAuthors() {

        List<Author> authors = authorRepository.findAll();
        var authorsConverted = authors.stream().map( mapper ::toDTO).toList();
        return authorsConverted;
    }

    public ResponseAuthorDTO findByIdAuthor(String id) {
        UUID uuId = UUID.fromString(id);
        Optional<Author> author = authorRepository.findById(uuId);
        if (author.isPresent()) {
            return  mapper.toDTO(author.get());
        } else {
            return null;
        }
    }

    public void deleteAuthorById(String id) {
        UUID uuid = UUID.fromString(id);
        Author authorExisted = authorRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found Author"));
        if(bookRepository.existsByAuthor(authorExisted)) {
            throw new AuthorWithBookException("This Author have books registred");
        }
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
             return authorRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
        }
        return listAuthors.stream().map(mapper::toDTO).collect(Collectors.toList());


    }
    public List<ResponseAuthorDTO> searchByExample(String nameAuthor, String nacionality) {
        Author author = new Author();
        author.setNameAuthor(nameAuthor);
        author.setNacionality(nacionality);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> authorExample = Example.of(author,matcher);
        return authorRepository.findAll(authorExample).stream().map(mapper::toDTO).collect(Collectors.toList());

    }

    public void updateAuthorById(String id, RequestAuthorDTO requestAuthorDTO) {
         Author authorExisted =  authorRepository.findById(UUID.fromString(id)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

         authorExisted.setNameAuthor(requestAuthorDTO.nameAuthor());
         authorExisted.setNacionality(requestAuthorDTO.nacionality());
         authorExisted.setDateOfBirth(requestAuthorDTO.dateOfBirth());
         authorRepository.save(authorExisted);



    }
}

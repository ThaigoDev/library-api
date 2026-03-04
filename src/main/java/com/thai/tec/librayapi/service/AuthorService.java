package com.thai.tec.librayapi.service;

import com.thai.tec.librayapi.domain.dtos.authorDTO.RequestAuthorDTO;
import com.thai.tec.librayapi.domain.dtos.authorDTO.ResponseAuthorDTO;
import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.exceptions.AuthorWithBookException;
import com.thai.tec.librayapi.mappers.AuthorMapper;
import com.thai.tec.librayapi.repositories.AuthorRepository;
import com.thai.tec.librayapi.repositories.BookRepository;
import com.thai.tec.librayapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
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
    private final AuthorMapper mapper;


    public ResponseAuthorDTO saveAuthor(RequestAuthorDTO requestAuthorDTO) {
            authorValidator.validator(requestAuthorDTO);

            Author author = mapper.toEntity(requestAuthorDTO);
            authorRepository.save(author);

            return mapper.toDTO(author);
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
             return authorRepository.findAll().stream().map(author -> new ResponseAuthorDTO(author.getId(), author.getNameAuthor(), author.getDateOfBirth(), author.getNacionality())).collect(Collectors.toList());
        }
        return listAuthors.stream().map(author -> new ResponseAuthorDTO(author.getId(), author.getNameAuthor(), author.getDateOfBirth(), author.getNacionality())).collect(Collectors.toList());


    }
    public List<ResponseAuthorDTO> searchByExample(String nameAuthor, String nacionality) {
        Author author = new Author();
        author.setNameAuthor(nameAuthor); 
        author.setNacionality(nacionality);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> authorExample = Example.of(author,matcher);
        return authorRepository.findAll(authorExample).stream().map(ath-> new ResponseAuthorDTO(ath.getId(),ath.getNameAuthor(),ath.getDateOfBirth(),ath.getNacionality())).collect(Collectors.toList());

    }

    public void updateAuthorById(String id, RequestAuthorDTO requestAuthorDTO) {
         Author authorExisted =  authorRepository.findById(UUID.fromString(id)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

         authorExisted.setNameAuthor(requestAuthorDTO.nameAuthor());
         authorExisted.setNacionality(requestAuthorDTO.nacionality());
         authorExisted.setDateOfBirth(requestAuthorDTO.dateOfBirth());
         authorRepository.save(authorExisted);



    }
}

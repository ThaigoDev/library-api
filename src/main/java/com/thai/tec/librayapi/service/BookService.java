package com.thai.tec.librayapi.service;

import com.thai.tec.librayapi.domain.dtos.bookDTO.RequestBookDTO;
import com.thai.tec.librayapi.domain.dtos.bookDTO.ResponseBookDTO;
import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.domain.entities.Book;
import com.thai.tec.librayapi.exceptions.DuplicatedRegisterException;
import com.thai.tec.librayapi.mappers.BookMapper;
import com.thai.tec.librayapi.repositories.AuthorRepository;
import com.thai.tec.librayapi.repositories.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper mapper;
    public ResponseBookDTO saveBook(@Valid RequestBookDTO requestBookDTO) {
        Author authorFinded=  authorRepository.findById(requestBookDTO.author()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));
        boolean bookExisted =  bookRepository.existsByAuthorAndTitle(authorFinded,requestBookDTO.title());
        if(bookExisted) {
            throw new DuplicatedRegisterException("this Books is duplicated");
        }

        Book book = mapper.toEntity(requestBookDTO);
        return   mapper.toDTO(bookRepository.save(book));
    }

    public ResponseBookDTO getById(UUID id) {

        return mapper.toDTO(bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, " Book Not found")));


    }

    public List<ResponseBookDTO> getAllBooksInSearch() {
        Specification<Book> specs = null;
        return  bookRepository.findAll(specs).stream().map(book -> mapper.toDTO(book)).collect(Collectors.toList());
    }

    public void deleteById(UUID id) {
        var bookExisted=  bookRepository.existsById(id);
        if(!bookExisted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        bookRepository.deleteById(id);
    }
}

package com.thai.tec.librayapi.controller;

import com.thai.tec.librayapi.domain.dtos.RequestAuthorDTO;
import com.thai.tec.librayapi.domain.dtos.ResponseAuthorDTO;
import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<ResponseAuthorDTO> save(@RequestBody  RequestAuthorDTO requestAuthorDTO)  {

        var author =  authorService.saveAuthor(requestAuthorDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(author.id()).toUri();

        return  ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseAuthorDTO> findAuthorById(@PathVariable("id") String id) {
         ResponseAuthorDTO author=  authorService.findByIdAuthor(id);
         return ResponseEntity.ok().body(author);

    }
    @GetMapping
    public  ResponseEntity<List<ResponseAuthorDTO>> getAllAuthors () {
        List<ResponseAuthorDTO> authorList = authorService.getAllAuthors();
        return ResponseEntity.ok().body(authorList);
    }
}

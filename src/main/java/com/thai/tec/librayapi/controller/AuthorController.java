package com.thai.tec.librayapi.controller;

import com.thai.tec.librayapi.domain.dtos.RequestAuthorDTO;
import com.thai.tec.librayapi.domain.dtos.ResponseAuthorDTO;
import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.service.AuthorService;
import org.apache.coyote.Response;
import org.springframework.data.repository.query.Param;
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
    public ResponseEntity<ResponseAuthorDTO> save(@RequestBody RequestAuthorDTO requestAuthorDTO) {

        var author = authorService.saveAuthor(requestAuthorDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(author.id()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseAuthorDTO> findAuthorById(@PathVariable("id") String id) {
        ResponseAuthorDTO author = authorService.findByIdAuthor(id);
        return ResponseEntity.ok().body(author);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") String id) {
        authorService.deleteAuthorById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseAuthorDTO>> search(
            @RequestParam(value = "nameAuthor", required = false) String nameAuthor,
            @RequestParam(value = "nacionality", required = false) String nacionality) {

        List<ResponseAuthorDTO> authors =  authorService.search(nameAuthor,nacionality);
        return  ResponseEntity.ok().body(authors);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateAtuthor(@PathVariable("id") String id,@RequestBody RequestAuthorDTO requestAuthorDTO) {
        authorService.updateAuthorById(id, requestAuthorDTO);
        return ResponseEntity.noContent().build();
    }
}

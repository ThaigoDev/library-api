package com.thai.tec.librayapi.controller;

import com.thai.tec.librayapi.controller.util.LocationURIGenerator;
import com.thai.tec.librayapi.domain.dtos.authorDTO.RequestAuthorDTO;
import com.thai.tec.librayapi.domain.dtos.authorDTO.ResponseAuthorDTO;
import com.thai.tec.librayapi.domain.entities.User;
import com.thai.tec.librayapi.mappers.AuthorMapper;
import com.thai.tec.librayapi.service.AuthorService;
import com.thai.tec.librayapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController implements LocationURIGenerator {
    private final AuthorService authorService;
    @PostMapping
    public ResponseEntity<ResponseAuthorDTO> save(@RequestBody @Valid RequestAuthorDTO requestAuthorDTO) {
        log.info("Cadastrando novo autor: {}", requestAuthorDTO.nameAuthor());
            var author = authorService.saveAuthor(requestAuthorDTO);
            URI location = generateHeaderLoation(author.id());

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
        log.info("Pesquisa autores");

        List<ResponseAuthorDTO> authors =  authorService.searchByExample(nameAuthor,nacionality);
        return  ResponseEntity.ok().body(authors);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateAtuthor(@PathVariable("id") String id,@RequestBody  @Valid  RequestAuthorDTO requestAuthorDTO) {
        authorService.updateAuthorById(id, requestAuthorDTO);
        return ResponseEntity.noContent().build();
    }
}

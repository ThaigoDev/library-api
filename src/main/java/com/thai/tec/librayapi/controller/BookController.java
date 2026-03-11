package com.thai.tec.librayapi.controller;

import com.thai.tec.librayapi.controller.util.LocationURIGenerator;
import com.thai.tec.librayapi.domain.dtos.bookDTO.RequestBookDTO;
import com.thai.tec.librayapi.domain.dtos.bookDTO.ResponseBookDTO;
import com.thai.tec.librayapi.domain.enums.GenderBook;
import com.thai.tec.librayapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController implements LocationURIGenerator {
    private final BookService bookService;

    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @PostMapping
    public ResponseEntity<ResponseBookDTO> saveBook(@RequestBody @Valid RequestBookDTO requestBookDTO) {

        ResponseBookDTO bookSaved = bookService.saveBook(requestBookDTO);
        URI location = generateHeaderLoation(bookSaved.id());

        return ResponseEntity.created(location).body(bookSaved);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseBookDTO> getBookById(@PathVariable("id") UUID id) {

        ResponseBookDTO book = bookService.getById(id);
        return ResponseEntity.ok().body(book);

    }


    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @GetMapping
    public ResponseEntity<Page<ResponseBookDTO>> searchBook(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "title", required = false)
            String title,
            @RequestParam(value = "gender", required = false)
            GenderBook gender,
            @RequestParam(value = "year", required = false)
            Integer year,
            @RequestParam(value = "nameAuthor", required = false)
            String nameAuthor,
            @RequestParam (value = "page", defaultValue = "0")
            Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10")
            Integer pageSize
    ) {

        Page<ResponseBookDTO> listBook = bookService.getAllBooksInSearch(isbn, title, gender, year,nameAuthor,page,pageSize);
        return ResponseEntity.ok().body(listBook);
    }



    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    public ResponseEntity<Void> updateById(@PathVariable("id") UUID id, @RequestBody RequestBookDTO dto){
         bookService.updateById(id, dto);

         return ResponseEntity.noContent().build();
    }

}

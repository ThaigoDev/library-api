package com.thai.tec.librayapi.controller;

import com.thai.tec.librayapi.controller.util.LocationURIGenerator;
import com.thai.tec.librayapi.domain.dtos.bookDTO.RequestBookDTO;
import com.thai.tec.librayapi.domain.dtos.bookDTO.ResponseBookDTO;
import com.thai.tec.librayapi.domain.entities.Book;
import com.thai.tec.librayapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController  implements LocationURIGenerator {
  private final BookService bookService;

    @PostMapping
    public ResponseEntity<ResponseBookDTO> saveBook(@RequestBody @Valid RequestBookDTO requestBookDTO) {

           ResponseBookDTO bookSaved  = bookService.saveBook(requestBookDTO);
           URI location = generateHeaderLoation(bookSaved.id());

           return  ResponseEntity.created(location).body(bookSaved);
    }
    @GetMapping("{id}")
    public ResponseEntity<ResponseBookDTO> getBookById(@PathVariable("id") UUID id) {

         ResponseBookDTO book = bookService.getById(id);
         return ResponseEntity.ok().body(book);

    }
    @GetMapping
    public ResponseEntity<List<ResponseBookDTO>> searchBook(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "nameAuthor", required = false)
            String nameAuthor,
            @RequestParam(value = "gender", required = false)
            String gender) {

        List<ResponseBookDTO> listBook = bookService.getAllBooksInDb();
        return ResponseEntity.ok().body(listBook);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

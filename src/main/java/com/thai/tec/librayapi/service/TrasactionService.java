package com.thai.tec.librayapi.service;

import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.domain.entities.Book;
import com.thai.tec.librayapi.domain.enums.GenderBook;
import com.thai.tec.librayapi.repositories.AuthorRepository;
import com.thai.tec.librayapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TrasactionService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;


    @Transactional
    public void saveBookWithImage() {
        //salva foto
        //repository.save(book)
        //pega o id do livro
        //var id = book.getId();

        //salvar foto do livro
        //buckerService.save(book.getId()+ ".png")

        //atualizar o nome do arquivo que foi salvo
        //book.setNameImage(id+ ".png")
     }
    @Transactional
    public void updateButNotUpdate() {
        var book = bookRepository.findById(UUID.fromString("965c31dd-ce56-4fe9-9b49-c712505b1c4d")).orElse(null);
        book.setDatepublic(LocalDate.of(2000,1,2) );
    }


    @Transactional
    public  void execute() {
        Book book = new Book();
        book.setIsbn("909090909-090909");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGener(GenderBook.FICCAO);
        book.setTitle("Sei lá das contas");
        book.setDatepublic(LocalDate.of(2000,2,1));

        Author author = new Author();
        author.setNameAuthor("Jogn");
        author.setNacionality("BR");
        author.setDateOfBirth(LocalDate.of(2000,2,2));
        var authorSaved =  authorRepository.save(author);
        book.setAuthor(authorSaved);
        var booksaved=  bookRepository.save(book);
    }

}

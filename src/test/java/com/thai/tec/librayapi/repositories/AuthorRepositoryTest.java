package com.thai.tec.librayapi.repositories;

import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.domain.entities.Book;
import com.thai.tec.librayapi.domain.enums.GenderBook;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    @Test
    public void saveAuthor() {
        Author author = new Author();
        author.setNameAuthor("Thiago");
        author.setNacionality("BR");
        author.setDateOfBirth(LocalDate.of(2000,2,2));
        var authorSaved =   authorRepository.save(author);
        System.out.println(authorSaved.toString());
     }

     @Test
     @Transactional
    public void updateAuthor() {
         var id  = UUID.fromString("ca0a42d2-ec8a-44cf-8a6b-663ab9df2625");
         Optional<Author> authorFinded = authorRepository.findById(id);
          if(authorFinded.isPresent()) {
              var author = authorFinded.get();
              author.setNameAuthor("teste");
             var authorUpdated=   authorRepository.save(author);
              System.out.println(authorUpdated.toString());
          }
    }
    @Test
    public void ListerAuthor() {
       List<Author> listOfAuthor = authorRepository.findAll();
       listOfAuthor.forEach(System.out::println);
    }
    @Test
    public void count() {
        Long count = authorRepository.count();
        System.out.println(count);
    }
    @Test
    public void deleteById(){
        var id = UUID.fromString("ca0a42d2-ec8a-44cf-8a6b-663ab9df2625");
        authorRepository.deleteById(id);
    }
    @Test
    public void deleteByObject() {
        var id=  UUID.fromString("ca0a42d2-ec8a-44cf-8a6b-663ab9df2625");
        var obj = authorRepository.findById(id).get();
        authorRepository.delete(obj);
    }

    @Test
    public void saveAuthorWithBooks() {
        Author author = new Author();
        author.setNameAuthor("Thiago");
        author.setNacionality("BR");
        author.setDateOfBirth(LocalDate.of(2000,2,2));

        Book book = new Book();
        book.setIsbn("909090909-090909");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGener(GenderBook.FICCAO);
        book.setTitle("Pequeno principe");
        book.setDatepublic(LocalDate.of(2000, 2, 1));
        book.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);

        authorRepository.save(author);
        bookRepository.saveAll(author.getBooks());

    }
    @Test
    @Transactional
    public void listBooksAuthors() {
        var id  = UUID.fromString("ca0a42d2-ec8a-44cf-8a6b-663ab9df2625");
        var author = authorRepository.findById(id).get();

         List<Book> list =  bookRepository.findByAuthor(author);

         author.setBooks(list);
         author.getBooks().forEach(System.out::println);
    }

}

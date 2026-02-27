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
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void save() {
        Book book = new Book();
        book.setIsbn("909090909-090909");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGener(GenderBook.FICCAO);
        book.setTitle("Sei lá das contas");
        book.setDatepublic(LocalDate.of(2000,2,1));

        Author author = authorRepository.findById(UUID.fromString("ca0a42d2-ec8a-44cf-8a6b-663ab9df2625")).orElse(null);

        book.setAuthor(author) ;
        bookRepository.save(book);
    }

    @Test
    void saveCascadeTest () {
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
        System.out.println(booksaved.toString());
    }

    @Test
    void updateAuthorToBook (){
        var  book =  bookRepository.findById(UUID.fromString("")).orElse(null);
        var authorFinded  = authorRepository.findById(UUID.fromString("ca0a42d2-ec8a-44cf-8a6b-663ab9df2625")).orElse(null);

        book.setAuthor(authorFinded);
        bookRepository.save(book);
    }
    @Test
    @Transactional
    void getAllBooks () {
        UUID id  = UUID.fromString("965c31dd-ce56-4fe9-9b49-c712505b1c4d");
        var book =  bookRepository.findById(id);
        System.out.println( "ID" + book.get().getId()+  "LIVRO: " + book.get().getTitle() + "Author: "+  book.get().getAuthor().getNameAuthor());

    }
    @Test
    void searchByName() {
        List<Book> list = bookRepository.findByTitleContaining("Sei ");
        list.forEach(System.out::println);
    }

//    @Test
//    void findWithJPQL () {
//        List<Book> list =  bookRepository.listBooks();
//        list.forEach(System.out::println);
//    }
//    @Test
//    void findAuthors () {
//        List<Author> list = bookRepository.listAuthors();
//        list.forEach(System.out::println);
//    }
//    @Test
//    void listByGener () {
//        List<Book> list = bookRepository.findByGener(GenderBook.FICCAO);
//        list.forEach(System.out::println);
//    }
//    @Test
//    void delete() {
//        bookRepository.deleteByGener(GenderBook.FICCAO);
//    }
}

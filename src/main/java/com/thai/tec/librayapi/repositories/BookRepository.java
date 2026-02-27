package com.thai.tec.librayapi.repositories;

import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
/**
 * @See BookRepositoryTest
 */


public interface BookRepository  extends JpaRepository<Book, UUID> {
 List<Book> findByAuthor(Author author);
 List<Book> findByTitleContaining(String title);
// @Query("select bk from Book as bk order by bk.title, bk.price  ")
// List<Book> listBooks();
 //aqui fazemos um inner join
// @Query("select a from Book bk join bk.autor a ")
// List<Author> listAuthors();
// @Query("""
//    select bk.gener
//    from Book bk
//    join bk.author a
//    where a.nacionality = 'BR'
//    order by bk.title
//
//""")
// List<String> listGenderAuthorsBrazilian();
// @Query("select bk from Book bk where bk.gener = ?1 ")
// List<Book> findByGener(GenderBook genderBook );
//
// @Modifying
// @Transactional
// @Query("delete from Book where gener = ?1")
// void deleteByGener(GenderBook genderBook);
//
// @Modifying
// @Transactional
// @Query("update Book set datepublish = ?1")
// void updateBook(LocalDate date);

}

package com.thai.tec.librayapi.validator;

import com.thai.tec.librayapi.domain.entities.Book;
import com.thai.tec.librayapi.exceptions.DuplicatedRegisterException;
import com.thai.tec.librayapi.exceptions.FielException;
import com.thai.tec.librayapi.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository bookRepository;
    private static final int DEFAULT_YEAR = 2020;

    public void  validar(Book book) {
        if(checkIsbnDuplicated(book)) {
            throw new DuplicatedRegisterException("The ISBN of Book exist in Database");
        }
        if(isPriceRequired(book)){
            throw  new FielException("price", "the price is required, please check your request");
        };
    }

    public  boolean checkIsbnDuplicated(Book book) {
        Optional<Book> bookFinded = bookRepository.findByIsbn(book.getIsbn());
        if(book.getId()==null) {
            return bookFinded.isPresent();
        }
        return bookFinded.map(Book::getId).stream().anyMatch(id-> !id.equals(book.getId()));

    }

    private boolean isPriceRequired(Book book) {
            return book.getId() ==null && book.getDatepublic().getYear() <= DEFAULT_YEAR;
    }

}

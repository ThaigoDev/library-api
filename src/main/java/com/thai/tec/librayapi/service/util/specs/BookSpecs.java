package com.thai.tec.librayapi.service.util.specs;

import com.thai.tec.librayapi.domain.entities.Book;
import com.thai.tec.librayapi.domain.enums.GenderBook;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSpecs {
    public static Specification<Book> isbnEqual(String isbn) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn));
    }

    public static Specification<Book> titleLike(String title) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder
                        .upper(root.get("title")), "%" + title.toUpperCase() + "%"));
    }

    public static Specification<Book> genderEquals(GenderBook gender) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gener"), gender));

    }

    public static Specification<Book> publishYearEqual(Integer publishYear) {
        return (root, query, cb) ->
                cb.equal(cb.function("to_char",
                                String.class, root.get("datepublic"),
                                cb.literal("YYYY")),
                        publishYear.toString());
    }

    public static Specification<Book> nameAuthorLike(String nameAuthor) {
        return ((root, query, criteriaBuilder) -> {
            Join<Object, Object> author = root.join("author", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.upper(author.get("nameAuthor")), "%"+ nameAuthor.toUpperCase()+"%");

//            return  criteriaBuilder.like(criteriaBuilder.upper(root.get("author").get("nameAuthor")),  "%" +nameAuthor.toUpperCase()+"%");
        });
    }
}

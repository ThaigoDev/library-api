package com.thai.tec.librayapi.domain.entities;

import com.thai.tec.librayapi.domain.enums.GenderBook;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "book")
@Data
@ToString(exclude = "author")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name="isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name="datepublish", nullable = false)
    private LocalDate datepublic;

    @Enumerated(EnumType.STRING)
    @Column(name = "gener", length = 30, nullable = false)
    private GenderBook gener;

    @Column(name = "price",precision = 18, scale = 2)
    private BigDecimal price;

    @ManyToOne (
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "id_author")
    private Author author;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;


}

package com.thai.tec.librayapi.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="author", schema = "public")
@Data
@ToString(exclude = "books")
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nameAuthor",length = 100, nullable = false)
    private String nameAuthor;

    @Column(name = "dateReborn", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "nacionality", length = 50, nullable = false)
    private String nacionality;

    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
    private List<Book> books;

}

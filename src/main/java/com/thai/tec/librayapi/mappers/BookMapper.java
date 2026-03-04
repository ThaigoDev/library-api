package com.thai.tec.librayapi.mappers;

import com.thai.tec.librayapi.domain.dtos.bookDTO.RequestBookDTO;
import com.thai.tec.librayapi.domain.dtos.bookDTO.ResponseBookDTO;
import com.thai.tec.librayapi.domain.entities.Book;
import com.thai.tec.librayapi.repositories.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {
    @Autowired
     public AuthorRepository repositoryAuthor;

    @Mapping(target = "author", expression ="java(repositoryAuthor.findById(requestBookDTO.author()).orElse(null))")
    public abstract Book toEntity (RequestBookDTO requestBookDTO);

    public  abstract ResponseBookDTO toDTO(Book book);

}

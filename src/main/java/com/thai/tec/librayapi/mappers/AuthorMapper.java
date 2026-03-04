package com.thai.tec.librayapi.mappers;

import com.thai.tec.librayapi.domain.dtos.authorDTO.RequestAuthorDTO;
import com.thai.tec.librayapi.domain.dtos.authorDTO.ResponseAuthorDTO;
import com.thai.tec.librayapi.domain.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(RequestAuthorDTO requestAuthorDTO);
    ResponseAuthorDTO toDTO(Author author);

}

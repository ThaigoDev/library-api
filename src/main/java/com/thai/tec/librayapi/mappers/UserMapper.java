package com.thai.tec.librayapi.mappers;

import com.thai.tec.librayapi.domain.dtos.userDTO.RequestUserDTO;
import com.thai.tec.librayapi.domain.dtos.userDTO.ResponseUserDTO;
import com.thai.tec.librayapi.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity (RequestUserDTO requestUserDTO);
    ResponseUserDTO toDTO (User user);
}

package com.thai.tec.librayapi.domain.dtos;

import com.thai.tec.librayapi.domain.entities.Author;

import java.time.LocalDate;
import java.util.UUID;

public record ResponseAuthorDTO (UUID id, String nameAuthor, LocalDate dateOfBirth, String nacionality) {

}

package com.thai.tec.librayapi.domain.dtos.authorDTO;

import com.thai.tec.librayapi.domain.entities.User;

import java.time.LocalDate;
import java.util.UUID;

public record ResponseAuthorDTO (UUID id, String nameAuthor, LocalDate dateOfBirth, String nacionality, User user) {

}

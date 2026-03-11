package com.thai.tec.librayapi.domain.dtos.bookDTO;

import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.domain.entities.User;
import com.thai.tec.librayapi.domain.enums.GenderBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate datepublic,
        GenderBook gender,
        BigDecimal price,
        Author author,
        User user,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {
}

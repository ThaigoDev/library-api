package com.thai.tec.librayapi.domain.dtos.bookDTO;

import com.thai.tec.librayapi.domain.enums.GenderBook;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RequestBookDTO(
        @NotBlank( message = "ISBN is required")
        String isbn,
        @NotBlank(message = "Title is required")
        String title,
        @NotNull(message = "Date is required")
        @Past(message = "invalid Date")
        LocalDate datepublic,
        @NotNull(message = "Gender book is required")
        GenderBook gener,
        @NotNull(message = "Price is required")
        BigDecimal price,
        @NotNull(message = "Author is required")
        UUID author


) {

}

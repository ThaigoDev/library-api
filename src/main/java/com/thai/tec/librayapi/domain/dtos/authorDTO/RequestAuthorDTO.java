package com.thai.tec.librayapi.domain.dtos.authorDTO;

import com.thai.tec.librayapi.domain.entities.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RequestAuthorDTO(
        @NotBlank(message = "Name is required")
        @Size(max = 100, min = 3 ,message = "length is incompatible")
        String nameAuthor,
        @NotNull(message = "Date of Birth is required")
        @Past(message = "date of birthday invalid")
        LocalDate dateOfBirth,
        @NotBlank(message = "Nacionality is required")
        @Size(min = 4, max = 20, message = "length is incompatible")
        String nacionality) {

  public Author mapToEntity() {
      Author author = new Author();
      author.setNameAuthor(this.nameAuthor);
      author.setNacionality(this.nacionality);
      author.setDateOfBirth(this.dateOfBirth);
      return author;
  }
}

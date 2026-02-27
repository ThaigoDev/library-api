package com.thai.tec.librayapi.domain.dtos;

import com.thai.tec.librayapi.domain.entities.Author;

import java.time.LocalDate;

public record RequestAuthorDTO(String nameAuthor, LocalDate dateOfBirth, String nacionality) {

  public Author mapToEntity() {
      Author author = new Author();
      author.setNameAuthor(this.nameAuthor);
      author.setNacionality(this.nacionality);
      author.setDateOfBirth(this.dateOfBirth);
      return  author;
  }
}

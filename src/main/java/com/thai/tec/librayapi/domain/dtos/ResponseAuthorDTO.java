package com.thai.tec.librayapi.domain.dtos;

import com.thai.tec.librayapi.domain.entities.Author;

public record ResponseAuthorDTO(String nameAuthor, String dateOfBirth, String nacionality) {
    public Author entityToDto() {
        Author author = new Author();
        author.setNameAuthor(this.nameAuthor);
        author.setNacionality(this.nacionality);
        author.setDateOfBirth(this.dateOfBirth);
        return  author;
    }
}

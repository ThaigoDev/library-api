package com.thai.tec.librayapi.validator;

import com.thai.tec.librayapi.domain.dtos.authorDTO.RequestAuthorDTO;
import com.thai.tec.librayapi.domain.entities.Author;
import com.thai.tec.librayapi.exceptions.DuplicatedRegisterException;
import com.thai.tec.librayapi.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {
    private AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
     public void validator(RequestAuthorDTO requestAuthorDTO) {
        if(verifyIfAuthorExist(requestAuthorDTO)){
              throw new DuplicatedRegisterException("Existed Author");
         }
     }
     private boolean verifyIfAuthorExist(RequestAuthorDTO author) {
        Optional<Author> authorFinded = authorRepository.findByNameAuthorAndDateOfBirthAndNacionality(author.nameAuthor(),author.dateOfBirth(),author.nacionality());
        if(authorFinded.isPresent()) {
            return true;
        }
        return false;

     }

}

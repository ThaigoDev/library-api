package com.thai.tec.librayapi.service;

import com.thai.tec.librayapi.domain.dtos.userDTO.RequestUserDTO;
import com.thai.tec.librayapi.domain.dtos.userDTO.ResponseUserDTO;
import com.thai.tec.librayapi.domain.entities.User;
import com.thai.tec.librayapi.exceptions.DuplicatedRegisterException;
import com.thai.tec.librayapi.mappers.UserMapper;
import com.thai.tec.librayapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    public  ResponseUserDTO save (RequestUserDTO requestUserDTO) {

        if(userRepository.existsByUsername(requestUserDTO.username()))    {
            throw  new DuplicatedRegisterException("Uer existed !");
        }
        User userConvertedToEntity =  mapper.toEntity(requestUserDTO);
        userConvertedToEntity.setPassword(encoder.encode(requestUserDTO.password()));

        return  mapper.toDTO( userRepository.save(userConvertedToEntity));
    }
    public User getByUsername(String username) {
         User user =  userRepository.findByUsername(username);
         if(user== null) {
             throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
         }
        return user;

    }

}

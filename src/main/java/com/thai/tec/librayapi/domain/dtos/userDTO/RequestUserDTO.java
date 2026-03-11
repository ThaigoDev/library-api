package com.thai.tec.librayapi.domain.dtos.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record RequestUserDTO(
        @NotBlank(message = "O nome não pode ser null")
        String username,
        @NotBlank(message =  "a senha não pode ser null")
        String password,
        @NotBlank(message = "Email não pode ser null")
        @Email(message = "insira um email valido")
        String email,
        @NotBlank( message = "Role não pode ser null")
        List<String> roles) {
}

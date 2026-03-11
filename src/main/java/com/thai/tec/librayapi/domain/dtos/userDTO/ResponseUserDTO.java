package com.thai.tec.librayapi.domain.dtos.userDTO;

import jakarta.validation.constraints.Email;

import java.util.List;
import java.util.UUID;

public record ResponseUserDTO (UUID id, String username, String email, List<String> roles){
}

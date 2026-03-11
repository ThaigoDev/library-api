package com.thai.tec.librayapi.domain.dtos.userDTO;

import java.util.List;

public record RequestUserDTO(String username, String password, List<String> roles) {
}

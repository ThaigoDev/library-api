package com.thai.tec.librayapi.domain.dtos.userDTO;

import java.util.List;
import java.util.UUID;

public record ResponseUserDTO (UUID id, String username, List<String> roles){
}

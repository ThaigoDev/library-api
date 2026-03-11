package com.thai.tec.librayapi.controller;

import com.thai.tec.librayapi.controller.util.LocationURIGenerator;
import com.thai.tec.librayapi.domain.dtos.userDTO.RequestUserDTO;
import com.thai.tec.librayapi.domain.dtos.userDTO.ResponseUserDTO;
import com.thai.tec.librayapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController implements LocationURIGenerator {
    private final UserService service;
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<ResponseUserDTO> save (@RequestBody  RequestUserDTO requestUserDTO) {
        ResponseUserDTO userSaved = service.save(requestUserDTO);
        return ResponseEntity.created( generateHeaderLoation(userSaved.id())).body(userSaved);
    }
}

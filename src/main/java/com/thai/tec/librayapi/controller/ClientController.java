package com.thai.tec.librayapi.controller;

import com.thai.tec.librayapi.domain.dtos.ClientCredentialsDTO.ClientCredentialsRequestDTO;
import com.thai.tec.librayapi.domain.entities.Client;
import com.thai.tec.librayapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ClientCredentialsRequestDTO client) {
        clientService.save(client);
        return ResponseEntity.ok().build();
    }

}

package com.thai.tec.librayapi.service;

import com.thai.tec.librayapi.domain.entities.Client;
import com.thai.tec.librayapi.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public Client save (Client client){
        var passwordEncoded  = encoder.encode(client.getClientSecret());
         client.setClientSecret(passwordEncoded);
        return  clientRepository.save(client);
    }

    public Client obterPorClientId(String clientId) {
        return  clientRepository.findByClientId(clientId);
    }
}

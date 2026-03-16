package com.thai.tec.librayapi.service;

import com.thai.tec.librayapi.domain.dtos.ClientCredentialsDTO.ClientCredentialsRequestDTO;
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

    public Client save(ClientCredentialsRequestDTO client){
        var passwordEncoded  = encoder.encode(client.clientSecret());

        Client clientEntity = new Client();

        clientEntity.setClientSecret(passwordEncoded);
        clientEntity.setClientId(client.clientId());
        clientEntity.setRedirectURI(client.redirectURI());
        clientEntity.setScope(client.scope());

        return  clientRepository.save(clientEntity);
    }

    public Client obterPorClientId(String clientId) {
        return  clientRepository.findByClientId(clientId);
    }
}

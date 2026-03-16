package com.thai.tec.librayapi.domain.dtos.ClientCredentialsDTO;

public record ClientCredentialsRequestDTO(
        String clientId,
        String clientSecret,
        String redirectURI,
        String scope
) {
}

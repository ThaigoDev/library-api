package com.thai.tec.librayapi.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.thai.tec.librayapi.security.CustomAuthentication;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {
    @Bean
    public TokenSettings tokenSettings () {
     return TokenSettings.builder()
             .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
             .accessTokenTimeToLive(Duration.ofMinutes(60))
             .refreshTokenTimeToLive(Duration.ofMinutes(90))
             .build();
    }

    @Bean
    public ClientSettings clientSettings () {
        return ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .requireProofKey(false)
                .build();
    }
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws  Exception {
        RSAKey rsaKey =  gerarChaveRSA(); //chave RSA é um tipo de chave usado em criptografia assimétrica.
        //É um metodo de criptografia  onde temos uma chave public e privada
        //temos o algoritmo RSA, ele é o mais seguro atualmente,  a chave public serve pra criptografar os dados
        //a chave privada serve pra descriptografar os dados, ela deve ser guardada.
        //somente o servidor sabe a chave privada, o Authorization Server, pois somente ele vai utiliar para descriptografar.

        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private RSAKey gerarChaveRSA() throws  Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA"); //aqui criamos o gerador e definimos qual algoritmo que vai ser usado

        keyPairGenerator.initialize(2048); //aqui, definimos os numeros de bits

        KeyPair keyPair = keyPairGenerator.generateKeyPair(); //aqui, geramos os pares de chaves.

        RSAPublicKey publicKey =   (RSAPublicKey ) keyPair.getPublic(); //aqui, nós pegamos as chaves publicas e privadas
        //é necessário fazer o casting para RSA PublicKey
        RSAPrivateKey privateKey =  (RSAPrivateKey)  keyPair.getPrivate();

     return new RSAKey.Builder(publicKey) //aqui, nós retornamos nossa RSAKey, utilozamos o Builder e passamos as chaves
             .privateKey(privateKey)
             .keyID(UUID.randomUUID().toString()) //geramos um id para essas  chaves.
             .build();

    }

    @Bean
    public JwtDecoder jwtDecoder (JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()

                .tokenEndpoint("/oauth2/token") //obter token
                // para consultar  o status do token
                .tokenIntrospectionEndpoint("/oauth2/introspect")
                //revogar o token
                .tokenRevocationEndpoint("/oauth2/revoke")
                //authorization endpoint
                .authorizationEndpoint("/oauth2/authorize")
                //pegar informações  do usuário OPEN ID CONNECT
                .oidcUserInfoEndpoint("/oauth2/iserinfo")
                //obter chave public para verificar assinatura do token
                .jwkSetEndpoint("/oauth2/jwks")
                .oidcLogoutEndpoint("/oauth2/logout")
                .build();
    }

    @Bean
     public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            //aqui pegamos o principal do contexto da autenticação
            var principal = context.getPrincipal();
            //verificamos se a autenticação é uma instancia de CustomAuthentication
            if(principal instanceof CustomAuthentication authentication) {
            //aqui pegamos o tipo do token
                OAuth2TokenType tipoToken = context.getTokenType();
                // verificamos se o tipo do token é de ACCESS TOKEN
                //ACCESS TOKEN : token que é enviado na requisição
                //REFRESH TOKEN : é um grant type, o refresh token emite um novo access token sem perder
                // a seção atual.
                if(OAuth2TokenType.ACCESS_TOKEN.equals(tipoToken)) {
                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

                    List<String> list = authorities.stream().map(GrantedAuthority::getAuthority).toList();
                   //Claims são as propriedades do token, aqui passamos as claims que queremos.
                    context
                            .getClaims()
                            .claim("authorities",list );
                }

            }
        };
    }
}

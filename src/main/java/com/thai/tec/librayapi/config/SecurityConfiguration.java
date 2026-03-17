package com.thai.tec.librayapi.config;

import com.thai.tec.librayapi.security.CustomUserDetailService;
import com.thai.tec.librayapi.security.JwtCustomAuthenticationFilter;
import com.thai.tec.librayapi.security.LoginSocialSucessHandler;
import com.thai.tec.librayapi.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)

public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain (
            HttpSecurity httpSecurity,
            LoginSocialSucessHandler loginSocialSucessHandler,
            JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter
    )  throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(oauthRS-> oauthRS.jwt(Customizer.withDefaults()))
                .formLogin(config -> config.loginPage("/login").permitAll())
                .oauth2Login(oauth2 ->
                        oauth2.loginPage("/login").successHandler(loginSocialSucessHandler)
                )
                .authorizeHttpRequests( authorize -> {
                    authorize.requestMatchers("/users/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return  new GrantedAuthorityDefaults(" ");
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter () {
        var authoritesConverter  = new JwtGrantedAuthoritiesConverter();
        authoritesConverter.setAuthorityPrefix(" ");

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritesConverter);

        return converter;

    }
}

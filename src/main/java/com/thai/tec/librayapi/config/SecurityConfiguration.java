package com.thai.tec.librayapi.config;

import com.thai.tec.librayapi.security.CustomUserDetailService;
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
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)

public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity, LoginSocialSucessHandler loginSocialSucessHandler)  throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(config -> config.loginPage("/login").permitAll())
                .oauth2Login(oauth2 ->
                        oauth2.loginPage("/login").successHandler(loginSocialSucessHandler)
                )
                .authorizeHttpRequests( authorize -> {
                    authorize.requestMatchers("/users/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    public UserDetailsService userDetailsService (UserService userService) {
     return new CustomUserDetailService(userService);
    }
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return  new GrantedAuthorityDefaults(" ");
    }
}

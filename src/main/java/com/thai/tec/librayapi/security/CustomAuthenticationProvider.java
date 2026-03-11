package com.thai.tec.librayapi.security;

import com.thai.tec.librayapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider  implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        var user =  userService.getByUsername(username);

        if(user ==null) {
            throw new UsernameNotFoundException("Usuário ou senha incorretos");
        }

        String  passwordEnconded = user.getPassword();

         if(encoder.matches(password, passwordEnconded))  {
          return  new CustomAuthentication(user);
         }else {
             throw new UsernameNotFoundException("Usuário ou senha incorretos");
         }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}

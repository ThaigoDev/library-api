package com.thai.tec.librayapi.security;


import com.thai.tec.librayapi.domain.dtos.userDTO.ResponseUserDTO;
import com.thai.tec.librayapi.domain.entities.User;
import com.thai.tec.librayapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          User user = userService.getByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[user.getRoles().size()]))
                .build();
    }
}

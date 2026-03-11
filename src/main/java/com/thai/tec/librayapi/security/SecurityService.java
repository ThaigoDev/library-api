package com.thai.tec.librayapi.security;

import com.thai.tec.librayapi.domain.entities.User;
import com.thai.tec.librayapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;
    public User  gerUser() {

        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof  CustomAuthentication customAuthentication) {
            return customAuthentication.getUser();
        }
     return null;
    }
}

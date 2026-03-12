package com.thai.tec.librayapi.security;

import com.thai.tec.librayapi.domain.dtos.userDTO.RequestUserDTO;
import com.thai.tec.librayapi.domain.entities.User;
import com.thai.tec.librayapi.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserService userService;
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken =  (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        User user  = userService.getByEmail(email);

        if(user == null) {
            RequestUserDTO userCreated =  new RequestUserDTO(oAuth2User.getName().toString(),"1234", email,List.of("OPERADOR") );

            userService.save(userCreated);
        }

        CustomAuthentication customAuthentication = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(customAuthentication);
        super.onAuthenticationSuccess(request,response,customAuthentication);
    }
}
